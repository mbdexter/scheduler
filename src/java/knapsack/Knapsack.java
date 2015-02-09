
package knapsack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.softtechdesign.ga.Crossover;
import com.softtechdesign.ga.GAException;
import com.softtechdesign.ga.GAString;

/**
 *
 * @author Marcin
 */
public class Knapsack extends GAString{

    static ArrayList<Term>[] users;
    static ArrayList<Range> possibleTerms = new ArrayList();
    static ArrayList<ArrayList<Integer>> selectedUsers = new ArrayList<ArrayList<Integer>>();
    static ArrayList<ArrayList<Term>> commonTerms = new ArrayList<ArrayList<Term>>();
    static int eventTime = 0;
    static int participantsNumber = 0;
    static int chromosomePopulation = 50;
    static double crosoverPropability = 0.9;
    static int selectionPropability = 80;
    static double mutationPropability = 0.01;
    static int stopAfterGenerations = 500;
    static int numPrelimRuns = 0;
    static int maxPrelimGenerations = 0;
    static int crosoverType =  Crossover.ctTwoPoint;
    static int currentTerm;
    static String validChromosome = "";
    static String[] results;
    
    Range fittestTerm = null;
    
    public Knapsack() throws GAException {
        super(
            participantsNumber,
            chromosomePopulation,
            crosoverPropability,
            selectionPropability,
            stopAfterGenerations,
            numPrelimRuns,
            maxPrelimGenerations,
            mutationPropability,
            0,
            "01",
            crosoverType,
            true
        );
    } 
    
    @Override
    public void run() {
        possibleTerms.add(
            new Range(
                Knapsack.dateToMinutes(Knapsack.stringToDate("2014-04-07 07:00:00")),
                Knapsack.dateToMinutes(Knapsack.stringToDate("2014-04-07 21:00:00"))
            )
        );
        
        possibleTerms.add(
            new Range(
                Knapsack.dateToMinutes(Knapsack.stringToDate("2014-05-04 08:00:00")),
                Knapsack.dateToMinutes(Knapsack.stringToDate("2014-05-04 19:00:00"))
            )
        );
        
        readDateFile();
        
        Range common;
        System.out.println("ilosc uczestników: " + participantsNumber);
        for(int x = 0; x < possibleTerms.size(); x++)
        {
            selectedUsers.add(new ArrayList<Integer>());
            commonTerms.add(new ArrayList<Term>());
            
            for(int i = 0; i < participantsNumber; i++)
            {
                for(int y = 0; y < users[i].size(); y++)
                {
                    common = users[i].get(y).intersection(possibleTerms.get(x));
                    
                    if(common != null && common.rangeSize() >= eventTime)
                    {
                        selectedUsers.get(x).add(i);
                        commonTerms.get(x).add(new Term(common, users[i].get(y).value));
                        break;
                    }
                }
            }
        }
        
        System.out.println(selectedUsers);
        
//        Knapsack.checkFitness("11111111111111111111111111111111111111111111111111111111111111111111111");
        
        results = new String[possibleTerms.size()];
        
        results[0] = "11000111111101111110110101111101110000111000000001011011000111011100111111111111111101111111101001100001111101101101011111101111100001100011111110111111011010111110111000011100000000101101100011101110011111111111111110111111110100110000111110110110101111110111110000";
        results[1] = "00111000100010001000101110000010001111000011000111000100110100100001001000100001100010001001010110001110000000010010110000000100101010011100010001000100010111000001000111100001100011100010011010010000100100010000110001000100101011000111000000001001011000000010010101";
        
        for(int t = 0; t < selectedUsers.size();t++){
            participantsNumber = selectedUsers.get(t).size();
            chromosomePopulation = Knapsack.roundToTen(participantsNumber / 2);
            stopAfterGenerations = Knapsack.roundToTen(participantsNumber * 5);
            currentTerm = t;
            Knapsack.thread();
        }
    }
    
    private static int roundToTen(int number)
    {
        number += 5;
        number /= 10;
        return number * 10;
    }
    
    private static Date stringToDate(String stringDate)
    {
        Date date = new Date();
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        try{
            date = df.parse(stringDate);
            
        }catch(ParseException e){
            e.printStackTrace();
        }
        
        return date;
    }
    
    private static Integer dateToMinutes(Date date)
    {
        return (int)(date.getTime() / 1000 / 60);
    }
    
    private static Date minutesToDate(Integer minutes)
    {
        return new Date((long)minutes * 60 * 1000);
    }
    
    @Override
    protected double getFitness(int chromosome)
    {
        double value = 0;
        String s = this.getChromosome(chromosome).getGenesAsStr();
        Range common = null;
        Range tmp = null;
        boolean first = true;
        int count = 0;
        String tmpValidChromosome = "";

        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) == '0')
            {
                tmpValidChromosome += "0";
                continue;
            }

            if(first)
            {
                common = commonTerms.get(currentTerm).get(i);
                tmp = common;
                first = false;
            }
            else
            {
                tmp = common;
                common = common.intersection(commonTerms.get(currentTerm).get(i));
            }

            if(common != null)
            {
                if(common.rangeSize() >= eventTime)
                {
                    tmpValidChromosome += "1";
                    value += commonTerms.get(currentTerm).get(i).value * 2;
                    count++;
                }
                else
                    tmpValidChromosome += "0";
            }
            else
            {
                common = tmp;
                tmpValidChromosome += "0";
            }
        }
        
        if(this.getFittestChromosomesFitness() < value * count)
        {
            fittestTerm = common;
            validChromosome = tmpValidChromosome;
        }
        
        this.getChromosome(chromosome).setGenesFromStr(tmpValidChromosome);
        
        return value * count;
    }
    
    protected static double checkFitness(String s)
    {
        double value = 0;
        Range common = null;
        boolean first = true;
        int count = 0;

        currentTerm = 0;
        
        System.out.println(s.length());
        
        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) == '0') continue;

            if(first)
            {
                common = commonTerms.get(currentTerm).get(i);
                first = false;
            }
            else
                common = common.intersection(commonTerms.get(currentTerm).get(i));

            if(common != null)
            {
                if(common.rangeSize() >= eventTime)
                {
                    value += commonTerms.get(currentTerm).get(i).value * 2;
                    count++;
                }
            }
            else
            {
                return 0;
            }
        }
        
        return value * count;
    }
    
    protected Date getEventStartTime()
    {
        return Knapsack.minutesToDate(fittestTerm.getLow().getValue());
    }
    
    protected Date getEventEndTime()
    {
        return Knapsack.minutesToDate(fittestTerm.getHigh().getValue());
    }
    
    static void thread()
    {
        Knapsack k = null;
        Thread thread;
        long startTime = System.currentTimeMillis();
        
        try
        {
            k = new Knapsack();
            thread = new Thread(k);
            thread.start();
            thread.join();
        }
        catch(Exception e)
        {
            System.out.println("Thread error: " + e.getMessage());
        }
        
        String s = k.getFittestChromosome() + "";
        
//        System.out.println(s);
        
        String finalChromosome = "";
        int userIndex = 0;
        
        for(int i = 0; i < users.length; i++)
        {
            if(selectedUsers.get(currentTerm).contains(i))
            {
                finalChromosome += validChromosome.charAt(userIndex);
                userIndex++;
            }
            else
                finalChromosome += "0";
        }
        
        System.out.println(finalChromosome);
//        System.out.println(results[currentTerm]);
        
        if(finalChromosome.equals(results[currentTerm]))
            System.out.println("Wynik prawidłowy");
        
        if(k.fittestTerm != null)
        {
            System.out.println(k.getEventStartTime());
            System.out.println(k.getEventEndTime());
        }
        else
        {
            System.out.println("Nie znaleziono rozwiązania");
        }
        
        System.out.println("Czas obliczeń: "+(System.currentTimeMillis() - startTime)+"ms");
        
    }
    
    static void readDateFile()
    {
//        FileReader fr = null;
//        try {
//            fr = new FileReader("dane4_mixed.txt");
//            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> buffer = new ArrayList();
            String s;
            int count = 0, index = 0;
            
//            eventTime = Integer.parseInt(s = br.readLine());
//            
//            while((s = br.readLine()) != null)
//            {
//                buffer.add(s);
//                count++;
//            }
            
            //Get file from resources folder
            ClassLoader classLoader = Knapsack.class.getClassLoader();
            File file = new File(classLoader.getResource("dane4_mixed.txt").getFile());
            
            try
            {
                Scanner scanner = new Scanner(file);
                
                if(scanner.hasNextLine())
                    eventTime = Integer.parseInt(scanner.nextLine());
                
                while(scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    buffer.add(line);
                    count++;
                }

                scanner.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            
            users = (ArrayList<Term>[])new ArrayList[count];
            
            for(String line : buffer)
            {
                String parts[] = line.split("\t");
                ArrayList<Term> tmpList = new ArrayList();
                
                for(int i = 1; i < parts.length; i++)
                {
                    String termParts[] = parts[i].split("#");
                    tmpList.add(
                        new Term(
                            Knapsack.dateToMinutes(Knapsack.stringToDate(termParts[0])),
                            Knapsack.dateToMinutes(Knapsack.stringToDate(termParts[1])),
                            Integer.parseInt(termParts[2])
                        )
                    );
                }
                users[index] = tmpList;
                index++;
            }
            
            participantsNumber = count;
            
//            br.close();
//            fr.close();
//        } catch (IOException e) {
//                System.out.println("Błąd podczas wczytywania pliku : "+e.getMessage());
//        }
    }
    
    static void readDateFile1()
    {
        FileReader fr = null;
        try {
            fr = new FileReader("dane4_mixed.txt");
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> buffer = new ArrayList();
            String s;
            int count = 0, index = 0;
            
            eventTime = Integer.parseInt(s = br.readLine());
            
            while((s = br.readLine()) != null)
            {
                buffer.add(s);
                count++;
            }
            
            users = (ArrayList<Term>[])new ArrayList[count];
            
            for(String line : buffer)
            {
                String parts[] = line.split("\t");
                ArrayList<Term> tmpList = new ArrayList();
                
                for(int i = 1; i < parts.length; i++)
                {
                    String termParts[] = parts[i].split("#");
                    tmpList.add(
                        new Term(
                            Knapsack.dateToMinutes(Knapsack.stringToDate(termParts[0])),
                            Knapsack.dateToMinutes(Knapsack.stringToDate(termParts[1])),
                            Integer.parseInt(termParts[2])
                        )
                    );
                }
                users[index] = tmpList;
                index++;
            }
            
            participantsNumber = count;
            
            br.close();
            fr.close();
        } catch (IOException e) {
                System.out.println("Błąd podczas wczytywania pliku : "+e.getMessage());
        }
    }
    
}
