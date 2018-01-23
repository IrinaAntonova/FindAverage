package ru.spbu.mas;

import java.util.*;

import jade.core.AID;
import jade.core.Agent;

public class DefaultAgent extends Agent {
    private LinkedHashSet<AID> inputAgents = new LinkedHashSet<>();
    private LinkedHashSet<AID> outputAgents = new LinkedHashSet<>();
    private AID finalAgent = new AID("7", AID.ISLOCALNAME);
    private float number = new Random().nextFloat();
    private HashMap<Integer, Float> map = new HashMap<Integer, Float>();
 // private LinkedHashSet<Integer> setOfLastIndex = new LinkedHashSet<>();
    private List<Float> listOfNum = new ArrayList<>();

    @Override
    protected void setup() {
        System.out.println("Agent " + getAID().getName() + " " + number);
        int id = Integer.parseInt(getAID().getLocalName());
        map.put(id, number);
       // setOfLastIndex.add(id);
        Object[] args = getArguments();
        int state = 0;
        for (Object o: args)
        {
            if(((String)o).equals("|"))
            {
                state = 1;
                continue;
            }
            switch (state) {
                case 0:
                    inputAgents.add(new AID((String)o, AID.ISLOCALNAME));
                    break;
                case 1:
                    outputAgents.add(new AID((String)o, AID.ISLOCALNAME));
                    break;
            }
        }
        addBehaviour(new FindAverage(this));
    }

    public AID getFinalAgent() {
        return finalAgent;
    }

    public int getCountOfIndexes() {
        return map.size();
    }

    public LinkedHashSet<AID> getInputAgents() {
        return inputAgents;
    }

    public LinkedHashSet<AID> getOutputAgents() {
        return outputAgents;
    }

    public String getLastValue() {
        String res = "";
        Integer[] val =  new Integer[map.size()];
        map.keySet().toArray(val);
        res += val[0] + "%" + map.get(val[0]);
        for (int i = 1; i < val.length; i++) {
            res += "|" + val[i] + "%" + map.get(val[i]);
        }
        return res;
    }

    public void setLastValue(String msgRec) {
        String[] arr = msgRec.split("\\|");
        for (String element : arr) {
            String[] pair = element.split("\\%");
            if (!map.containsKey(Integer.parseInt(pair[0]))) map.put(Integer.parseInt(pair[0]), Float.parseFloat(pair[1]));
        }
    }

    public float getAverage() {
        float sum = 0;
        for (float element : map.values())
            sum += element;
        return sum / map.size();
    }


}