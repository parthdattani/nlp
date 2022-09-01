package com.parth.nlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class pipeline {
    public static String reader(){
        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get("C:\\Users\\ual-laptop\\Desktop\\abcdefghi.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    public static int sentencesCnt(String text){
        int i = 0;
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument doc = new CoreDocument(text);
        pipeline.annotate(doc);
        for (CoreSentence sent : doc.sentences()) {
            i = i + 1;
        }
        return i;
    }
    public static void writers(int a, int[] t){
        int avgWords = parseInt(String.valueOf(t[0]/a));
        String typeOfSentence = "";
        String avgAdjectives = (String.valueOf(t[2]/a));
        String avgAdverb = (String.valueOf((t[3]/a)));
        String avgVerb = (String.valueOf((t[4]/a)));

        try {
            FileWriter myWriter = new FileWriter("C:\\Users\\ual-laptop\\Desktop\\abc.txt");
            myWriter.write("Number of sentences = "+a+'\n');
            myWriter.write("Number of Words = "+t[0]+'\n');
            myWriter.write("Number of Nouns = "+t[1]+'\n');
            myWriter.write("Number of Adjective = "+t[2]+'\n');
            myWriter.write("Number of Adverb = "+t[3]+'\n');
            myWriter.write("Number of Verb = "+t[4]+'\n');
            myWriter.write("Standard number of words a sentence should have = "+17+'\n');
            myWriter.write("STATISTICS"+'\n');
            myWriter.write("Average Number of words per sentence = "+avgWords+'\n');
            if(avgWords<=8){
                typeOfSentence = "Very easy to read";
            } else if(9<=avgWords && avgWords<=11){
                typeOfSentence = "Easy";
            } else if(12<=avgWords && avgWords<=14){
                typeOfSentence = "Fairly Easy";
            } else if(15<=avgWords && avgWords<=17){
                typeOfSentence = "Standard";
            } else if(18<=avgWords && avgWords<=21){
                typeOfSentence = "Fairly Difficult";
            } else if(22<=avgWords && avgWords<=25){
                typeOfSentence = "Difficult";
            }
            myWriter.write("Type of Sentences = "+typeOfSentence+'\n');
            myWriter.write("Adjectives per Sentence = "+avgAdjectives+'\n');
            myWriter.write("Adverb per Sentence = "+avgAdverb+'\n');
            myWriter.write("Verb per Sentence = "+avgVerb+'\n');
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static int[] wordCnt(String text) {
        int[] j ={0,0,0,0,0};// Array to throw count of words, noun, adjective, adverb and verb
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = pipeline.processToCoreDocument(text);
        for (CoreLabel tok : document.tokens()) {
            j[0]=j[0]+1;
            String a = tok.tag();
            if(a.equals("NN")||a.equals("NNS")||a.equals("NNPS")||a.equals("NNP")){
                j[1]=j[1]+1;
            }
            else if(a.equals("JJ")||a.equals("JJR")||a.equals(  "JJS")){
                j[2]=j[2]+1;
            }
            else if(a.equals("RB")||a.equals("RBR")||a.equals("RBS")){
                j[3]=j[3]+1;
            }
            else if(a.equals("VB")||a.equals("VBD")||a.equals("VBG")||a.equals("VBN")||a.equals("VBP")||a.equals("VBZ")){
                j[4]=j[4]+1;
            }
        }
        return j;
    }
    public static void main(String[] args) {
        String text = reader();
        int SentenceCnt = sentencesCnt(text);
        int[] WordCnt = wordCnt(text);
        writers(SentenceCnt, WordCnt);
    }
}
