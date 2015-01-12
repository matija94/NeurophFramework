/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.neuroph.ocr.properties;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;
import org.neuroph.ocr.util.OCRCropImage;
import org.neuroph.ocr.util.OCRUtilities;
import org.neuroph.ocr.util.Word;

/**
 *
 * @author Mihailo Stupar
 */
public class RecognitionProperties extends Properties {
    
    private String recognizedTextPath;
    private NeuralNetwork nnet;
    private ImageRecognitionPlugin plugin;
    private String text;
    
    private boolean[][] visited;  //sluzi samo za procesiranje slike, za BFS

    public RecognitionProperties(LetterInformation letterInformation, TextInformation textInformation) {
        super(letterInformation, textInformation);
    }

    /**
     * Path to the .txt folder where the recognized text will be stored
     *
     * @param recognizedTextPath
     */
    public void setRecognizedTextPath(String recognizedTextPath) {
        this.recognizedTextPath = recognizedTextPath;
    }

    /**
     * @param nnet trained neural network
     */
    public void setNeuralNetwork(NeuralNetwork nnet) {
        this.nnet = nnet;
        plugin = (ImageRecognitionPlugin) nnet.getPlugin(ImageRecognitionPlugin.class);
    }

    /**
     * @param path path of the trained neural network
     */
    public void setNetworkPath(String networkPath) {
        nnet = NeuralNetwork.createFromFile(networkPath);
        plugin = (ImageRecognitionPlugin) nnet.getPlugin(ImageRecognitionPlugin.class);
    }

    /**
     * recognize the text on the image (document)
     */
    public void recognize() {
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        visited = new boolean[imageHeight][imageWidth];
        text = "";
        
        for (int i = 0; i < textInformation.numberOfRows(); i++) {
            text += recognizeRow(i) + "\n";
        }
        
        visited = null; //prevent lottering
    }
    
    private String recognizeRow(int row) {
        String rowText = "";
        List<Word> words = textInformation.getWordsAtRow(row);
        
        for (int i = 0; i < words.size(); i++) {
            int rowPixel = textInformation.getRowAt(row);
            Word word = words.get(i);
            rowText += recognizeWord(word, rowPixel);
            
            if ((i + 1) == words.size()) { //trenutno smo na poslednjoj reci u redu
                rowText += addSpaces(word, null);
            } else {
                Word next = words.get(i + 1);
                rowText += addSpaces(word, next);
            }
        }
        return rowText;
        
    }
    
    private String recognizeWord(Word word, int rowPixel) {
        String wordText = "";
        
        int tmpWidth = 3 * letterInformation.getCropWidth();
        int tmpHeight = 3 * letterInformation.getCropHeight();
        int letterSize = letterInformation.getLetterSize();
        
        int start = word.getStartPixel();
        int end = word.getEndPixel();
        
        Color white = Color.WHITE;
        Color color;
        
        for (int k = -(letterSize / 4); k < (letterSize / 4); k++) {
            int i = rowPixel + k;
            if (i < 0 || i > image.getHeight()) {
                continue;
            }
            for (int j = start; j < end; j++) {
                
                color = new Color(image.getRGB(j, i));
                if (color.equals(white)) {
                    visited[i][j] = true;
                } else if (visited[i][j] == false) {
                    BufferedImage letter = OCRUtilities.extraxtCharacterImage(image, visited, i, j, tmpWidth, tmpHeight, letterInformation.getTrashSize());
                    if (letter != null) {
                        OCRCropImage crop = new OCRCropImage(letter, letterInformation.getCropWidth(), letterInformation.getCropHeight());
                        BufferedImage croped = crop.processImage();
                        wordText += recognizeLetter(croped);
                    }
                }
            }
        }
        return wordText;
    }
    
    private String recognizeLetter(BufferedImage image) {
        // samo za test 
//        OCRUtilities.saveToFile(image, "C:\\Users\\Mihailo\\Desktop\\OCR\\test-letters", new Random().nextInt()+"", "png");
        //
        Map<String, Double> output = plugin.recognizeImage(image);
        return OCRUtilities.getCharacter(output);
    }
    
    private String addSpaces(Word first, Word second) {
        if (second == null) {
            return "";
        }
        String space = "";
        int gap = second.getStartPixel() - first.getEndPixel();
        int num = gap / letterInformation.getSpaceGap();
        
        for (int i = 0; i < num; i++) {
            space += " ";
        }
        return space;
    }

    /**
     * @return recognized text from the image
     */
    public String getRecognizedText() {
        return text;
    }
    
    /**
     * save the recognized text to the file specified earlier in location folder
     */
    public void saveText() {
        try {
            File file = new File(recognizedTextPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            String[] lines = text.split("\n");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}
