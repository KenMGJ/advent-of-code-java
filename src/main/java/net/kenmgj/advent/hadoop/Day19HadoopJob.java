package net.kenmgj.advent.hadoop;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Day19HadoopJob extends Configured implements Tool {

	public static class Mapr extends MapReduceBase implements
	Mapper<LongWritable, Text, Text, NullWritable> {

		private static final Log logger = LogFactory.getLog(Day19HadoopJob.class.getName() + ".Map");

		private Map<String, String> replacements;

		@Override
		public void configure(JobConf job) {
			super.configure(job);

			List<String> fileContents = new ArrayList<String>();
			replacements = new HashMap<String, String>();

			try {
				Path[] uris = DistributedCache.getLocalCacheFiles(job);
				fileContents = FileUtils.readLines(new File(uris[0].toString()));
			} catch (Exception e) {
				logger.info("Exception: " + e);
			}
			logger.info("Cache contents: " + fileContents);

			for (String line : fileContents) {
				String[] keyAndValue = line.split("\t");
				replacements.put(keyAndValue[0], keyAndValue[1]);
			}

			logger.info("Replacement size: " + replacements.size());
		}

		public void map(LongWritable key, Text value, OutputCollector<Text, NullWritable> output, Reporter reporter)
				throws IOException {

			String molecule = value.toString();

			for (Entry<String, String> replacement : replacements.entrySet()) {
				if (molecule.contains(replacement.getKey())) {
					int index = molecule.indexOf(replacement.getKey());
					while ( index >= 0) {
						String begin = (index == 0) ? "" : molecule.substring(0, index);
						int offset = index + replacement.getKey().length();
						String end = (offset == molecule.length()) ? "" : molecule.substring(offset);
						output.collect(new Text(begin + replacement.getValue()+ end), NullWritable.get());
						index = molecule.indexOf(replacement.getKey(), index);
					}
				}
			}

		}
	}

	public static class Redr extends MapReduceBase implements
	Reducer<Text, NullWritable, Text, NullWritable> {

		public void reduce(Text key, Iterator<NullWritable> values, OutputCollector<Text, NullWritable> output,
				Reporter reporter) throws IOException {
			output.collect(key, NullWritable.get());
		}

	}

	private static final String USAGE = "<REPLACEMENT_FILE> <INPUT_FILE> <OUTPUT_DIR>";

	public int run(String[] args) throws Exception {

		if (args.length == 3) {
			System.err.println(USAGE);
			System.exit(2);
		}

		JobConf conf = new JobConf(getConf(), getClass());
		conf.setJobName(getClass().getSimpleName());

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		DistributedCache.addCacheFile(new URI(args[0]), conf);

		FileInputFormat.setInputPaths(conf, new Path(args[1]));
		FileOutputFormat.setOutputPath(conf, new Path(args[2]));

		conf.setMapperClass(Mapr.class);
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(NullWritable.class);

		conf.setReducerClass(Redr.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(NullWritable.class);

		JobClient.runJob(conf).waitForCompletion();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new Day19HadoopJob(), args);
	}

}
