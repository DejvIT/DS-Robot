/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robots;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author davidpavlicko
 */
public abstract class Game {
    
    private int kSteps;
    private boolean customAlgorithm;
    private boolean stopped = false;
    private int replications;
    
    protected int xSize;
    protected int ySize;
    protected int passedReplications;
    
    public abstract void replication();
    public abstract void spiralTrace();

    public int getXSize() {
        return this.xSize;
    }

    public int getYSize() {
        return this.ySize;
    }

    public int getKSteps() {
        return this.kSteps;
    }

    public void setCustomAlgorithm(boolean state) {
        this.customAlgorithm = state;
    }

    public boolean getCustomAlgorithm() {
        return this.customAlgorithm;
    }

    public int getReplications() {
        return this.replications;
    }
    
    public int getPassedReplications() {
        return this.passedReplications;
    }
    
    public void simulate(Interface afterReplication, Interface afterSimulation) {
      
        for (int i = this.passedReplications; i < this.replications; i++) {
            if (!this.stopped) {
                if (this.customAlgorithm) {
                    spiralTrace();  
                } else {
                    replication();  
                } 
                afterReplication.call();                
            } else {
                return;
            }            
        }
        
        new Thread(() -> {
            afterSimulation.call();
        }).start(); 
        
    }
    
    public void setStopped(boolean state) {
        this.stopped = state;
    }
    
    public void setReplications(int replications) {
        this.replications = replications;
    }
    
    public void setPassedReplications(int passedReplications) {
        this.passedReplications = passedReplications;
    }
    
    public void setXSize(int width) {
        this.xSize = width;
    }
    
    public void setYSize(int height) {
        this.ySize = height;
    }
    
    public void setKSteps(int steps) {
        this.kSteps = steps;
    }
}
