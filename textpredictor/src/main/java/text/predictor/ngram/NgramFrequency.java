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

public class NgramFrequency {
	  public static void main(String[] args) {
	        Options options = new Options();
	        options.addOption("outDir",true,"choose file");
	        CommandLineParser clp = new DefaultParser();
	        try {
				CommandLine cmd = clp.parse(options, args);
			    NgramFrequency nf = new NgramFrequency(); 
				nf.writeToFile("outfrequecny.txt", nf.saveTokenFrequencies("ngramfile.txt"));  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
	  } 
	  public void writeToFile(String outfile, Map<String, Integer> tokenfrequencymap){
		  FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(outfile));
	 	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		    tokenfrequencymap.forEach((word, num) ->{ 
				try {
					bw.write(word + ":"+num);
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
	  public Map<String, Integer> saveTokenFrequencies(String infile) throws IOException{
		  InputStream instream = NgramFrequency.class.getClassLoader().getResourceAsStream(infile);
		  Map<String, Integer> tokenfrequencymap = new TreeMap<>();
		  List<String> lines = IOUtils.readLines(instream);
		  for (String line : lines){
			  String[] tokens = line.trim().replace("[^A-Za-z0-9]", " ").split(" ");
			  for(String token : tokens){
				  if(StringUtils.isNotBlank(token) && token.length() > 1){
					  Integer frequency = tokenfrequencymap.get(token);
					  if(frequency == null)
						  frequency = 0;
					  tokenfrequencymap.put(token, ++frequency);
					  
				  }
			  }
		  } 
		  return tokenfrequencymap;
	  }
}

