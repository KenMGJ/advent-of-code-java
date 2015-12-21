package net.kenmgj.advent.hadoop;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.kenmgj.advent.hadoop.Day19HadoopJob.Mapr;
import net.kenmgj.advent.hadoop.Day19HadoopJob.Redr;


public class Day19HadoopJobTest {

	private Mapr mapper;
	private MapDriver<LongWritable, Text, Text, NullWritable> mapDriver;

	private Redr reducer;
	private ReduceDriver<Text, NullWritable, Text, NullWritable> reduceDriver;

	private File cacheFile;

	protected String baseDir = System.getProperty("java.io.tmpdir") + File.separator + getClass().getSimpleName();

	@Before
	public void setUp() throws Exception {
		JobConf conf = new JobConf();

		mapper = new Mapr();
		mapper.configure(conf);
		mapDriver = new MapDriver<LongWritable, Text, Text, NullWritable>();
		mapDriver.withMapper(mapper);

		reducer = new Redr();
		reducer.configure(conf);
		reduceDriver = new ReduceDriver<Text, NullWritable, Text, NullWritable>();
		reduceDriver.withReducer(reducer);

		createLookupFile();
		DistributedCache.setLocalFiles(conf, cacheFile);
	}

	@After
	public void cleanUp() {
		cacheFile.delete();
	}

	private void createLookupFile() throws IOException {
		cacheFile = File.createTempFile("Day19HadoopJobTest-" + Long.toString(System.currentTimeMillis()), ".txt");

		if (!(cacheFile.delete())) {
			throw new IOException("Could not delete temp file: " + cacheFile.getAbsolutePath());
		}
		if (!(cacheFile.createNewFile())) {
			throw new IOException("Could not create temp file: " + cacheFile.getAbsolutePath());
		}

		FileUtils.writeStringToFile(cacheFile, "H\te\n");
		FileUtils.writeStringToFile(cacheFile, "O\te\n");
		FileUtils.writeStringToFile(cacheFile, "HO\tH\n");
		FileUtils.writeStringToFile(cacheFile, "OH\tH\n");
		FileUtils.writeStringToFile(cacheFile, "HH\tO\n");
	 }

	@Test
	public void testMapper() throws IOException {
		List<Pair<Text, NullWritable>> result = mapDriver.withInput(null, new Text("HOHOHO")).run();
		Assert.assertEquals(1, result.size());
	}
}
