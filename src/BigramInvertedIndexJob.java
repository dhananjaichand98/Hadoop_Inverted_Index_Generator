import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BigramInvertedIndexJob {

  public static class BigramInvertedIndexMapper
      extends Mapper<Object, Text, Text, Text> {

    private Text word = new Text();

    private String preprocess(String text) {
      text = text.replaceAll("[^A-Za-z]", " ");
      text = text.toLowerCase();
      return text;
    }

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

      String splitVal[] = value.toString().split("\\t", 2);
      Text docId = new Text(splitVal[0]);

      String text = splitVal[1];
      text = preprocess(text);
      text = text.trim();

      String splitText[] = text.split("\\s+");
      for(int i=1; i<splitText.length; i++){
        String firstWord = splitText[i-1];
        String secondWord = splitText[i];
        String s = firstWord + " " + secondWord;
        word.set(s);
        context.write(word, docId);
      }

    }
  }

  public static class BigramInvertedIndexReducer
      extends Reducer<Text, Text, Text, Text> {
    private Text result = new Text();

    public void reduce(Text key, Iterable<Text> values,
        Context context) throws IOException, InterruptedException {
      
      Map<String, Integer> m = new HashMap<>();

      for (Text val : values) {
        String docID = val.toString();
        m.put(docID, m.getOrDefault(docID, 0) + 1);
      }

      StringBuilder sb = new StringBuilder();
      // sb.append("\t");
      for (Map.Entry<String, Integer> entry : m.entrySet()) {
        sb.append(entry.getKey());
        sb.append(":");
        sb.append(entry.getValue().toString());
        sb.append(" ");
      }

      result.set(sb.substring(0, sb.length() - 1));
      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: Inverted Index <input path> <output path>");
      System.exit(-1);
    }

    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "inverted index");
    job.setJarByClass(BigramInvertedIndexJob.class);
    job.setMapperClass(BigramInvertedIndexMapper.class);
    job.setReducerClass(BigramInvertedIndexReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    
    String ipFile = args[0];
    String opFile = args[1];
    
    FileInputFormat.addInputPath(job, new Path(ipFile));
    FileOutputFormat.setOutputPath(job, new Path(opFile));

    System.exit(job.waitForCompletion(true) ? 0 : -1);
  }
}