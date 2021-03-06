

	package text.predictor.ngram;

	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.OutputStreamWriter;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Map;
	import java.util.TreeMap;

	import org.apache.commons.cli.CommandLine;
	import org.apache.commons.cli.CommandLineParser;
	import org.apache.commons.cli.DefaultParser;
	import org.apache.commons.cli.Options;
	import org.apache.commons.cli.ParseException;
	import org.apache.commons.io.IOUtils;
	import org.apache.commons.lang3.StringUtils;


    public class NgramBuilder { 
		  public static void main(String[] args) {
		        Options options = new Options();
		        options.addOption("outDir",true,"choose file");
		        CommandLineParser clp = new DefaultParser();
		        try {
					CommandLine cmd = clp.parse(options, args);
					NgramBuilder nf = new NgramBuilder();   
					nf.createNgrams(2,"ngramfile.txt",  "bigrams.txt" );
					nf.createNgrams(3,"ngramfile.txt",  "trigrams.txt" );
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		  }
		  public void createNgrams(int n, String infile, String outfile){ 
			  try {
				writeToFile(outfile,    createngrams(n, infile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  public List<String > createngrams(int n, String infile) throws IOException{
			  InputStream instream = NgramFrequency.class.getClassLoader().getResourceAsStream(infile);
			  List<String > ngramslist = new ArrayList<>();
			  List<String> lines = IOUtils.readLines(instream);
			  for (String line : lines){
				  ngramslist.addAll(ngrams(n, line));
			  }
			  return ngramslist;
		} 
			 
		  
		  private  List<String> ngrams(int n, String str) {
		        List<String> ngrams = new ArrayList<String>();
//		        if(str.length() <= 0 ) return ngrams;
		        String[] words = str.split(" ");
		        if(words.length >2)
		          for (int i = 0; i < words.length - n + 1; i++)
		              ngrams.add(concat(words, i, i+n));
		        return ngrams;
		  }
		  private  String concat(String[] words, int start, int end) {
		        StringBuilder sb = new StringBuilder();
		        for (int i = start; i < end; i++)
		            sb.append((i > start ? " " : "") + words[i]);
		        return sb.toString();
		  }
		  public void writeToFile(String outfile, List<String> ngrams){
			  FileOutputStream fos;
			try {
				fos = new FileOutputStream(new File(outfile));
		 	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			    ngrams.forEach((word ) ->{ 
					try {
						bw.write(word );
						bw.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			  });
			  bw.close(); 
			  fos.close(); 
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
 
	}

