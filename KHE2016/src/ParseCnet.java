
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joshreichman, Emilio Lopez
 */
public class ParseCnet implements ParseWebPage {


    public ParseCnet() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String websiteTitle(Document d) throws IOException {
        // TODO Auto-generated method stub
        return ("Title: " + d.title());
    }

    @Override
    public String findArticleBody(Document d) throws IOException {
        // TODO Auto-generated method stub
        // Not Complete!
        throw new UnsupportedOperationException();
    }

    @Override
    public String findPros(Document d) throws IOException {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        String title = d.select("div.quickinfo > *").toString();        
        title = title.replaceAll("\\<.*?>", "");
        title = title.substring(8);
        
        // The Good section of a review is separated by a new line 
        for (int i = 0; i < title.length(); i++){
            if (title.charAt(i) != '\n')
                sb.append(title.charAt(i));
            else
                break;
        }
        
        return("Pros: " + sb.toString()); 
    }

    @Override
    public String findCons(Document d) throws IOException {
        // TODO Auto-generated method stub
        int newLineCounter = 0;
        
        StringBuilder sb = new StringBuilder();
        String title = d.select("div.quickinfo > *").toString();        
        title = title.replaceAll("\\<.*?>", "");
        
        
        /* Filters the Bad part of the summary from the Good and Bottomline*/
        for (int i = 0; i < title.length(); i++){
            
            if (newLineCounter == 1)
                sb.append(title.charAt(i));
                        
            if (title.charAt(i) == '\n')
                newLineCounter++;
            
            if (newLineCounter > 1)
                break;            
        }        
        String returnString = sb.toString().substring(7);
        return("Cons: " + returnString); 
    }
    
    public static void main(String[] args) throws IOException, IllegalArgumentException {
        // TODO Auto-generated method stub
        ParseCnet parseCnet = new ParseCnet();
        Scanner input = new Scanner(System.in);
        Document d = null;
        System.out.print("input url: ");
        String url = input.next();
        input.close();
        try{
            d = Jsoup.connect(url).timeout(6000).get();
        }
        catch(Exception e){
           System.out.println("ERROR! Invalid URL!"); 
           System.exit(0);
        }
        System.out.println("Success! Valid URL!");
        System.out.println(parseCnet.websiteTitle(d) + "\n");
        System.out.println(parseCnet.findPros(d) + "\n");
        System.out.println(parseCnet.findCons(d) + "\n");
    }
}