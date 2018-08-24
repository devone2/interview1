package interview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static class Synonyms {
		
		private Map<String,Set<String>> map = new HashMap<>();
		
		private String normalize(String w)
		{
			return w.toLowerCase();
		}
		
		public void add(String word1, String word2)
		{
			String w1 = normalize(word1);
			String w2 = normalize(word2);
			
			if(!map.containsKey(w1) && !map.containsKey(w2)) {
				Set<String> s = new HashSet<>();
				s.add(w1);
				s.add(w2);
				map.put(w1, s);
				map.put(w2, s);
			} else if(map.containsKey(w1) && !map.containsKey(w2)) {
				Set<String> s = map.get(w1);
				s.add(w2);
				map.put(w2, s);
			} else if(!map.containsKey(w1) && map.containsKey(w2)) {
				Set<String> s = map.get(w2);
				s.add(w1);
				map.put(w1, s);
			} else {
				Set<String> s1 = map.get(w1);
				Set<String> s2 = map.get(w2);
				
				if(s1!=s2) {
					s1.addAll(s2);
					
					for(String s : s2) {
						map.put(s, s1);
					}
				}
			}
		}
		
		public boolean areSynonyms(String word1, String word2)
		{
			String w1 = normalize(word1);
			String w2 = normalize(word2);
			return w1.equals(w2) || (map.containsKey(w1) && map.get(w1).contains(w2));
		}
		
	}
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		Scanner sc = new Scanner(new File("test.in"));
		PrintWriter writer = new PrintWriter("test.out", "UTF-8");
		
		int testCases = sc.nextInt();
		for(int i=0; i<testCases; i++) {
			int dictSize = sc.nextInt();
			Synonyms syns = new Synonyms();
			for(int j=0;j<dictSize;j++) {
				String w1 = sc.next();
				String w2 = sc.next();
				//System.out.println("Pair: (" + w1 + ", "+ w2+")");
				syns.add(w1, w2);
			}
			int qs = sc.nextInt();
			for(int j=0;j<qs; j++) {
				String w1 = sc.next();
				String w2 = sc.next();
				writer.println(syns.areSynonyms(w1, w2) ? "synonyms" : "different");
			}
		}
				
		sc.close();
		writer.close();

	}
	
	

}
