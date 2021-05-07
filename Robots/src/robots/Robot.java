/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robots;

import java.util.ArrayList;

/**
 *
 * @author davidpavlicko
 */
public abstract class Robot extends Game {
    
    private int startPoint;
    protected ArrayList<Integer> path = new ArrayList<>();
    protected boolean collision = false;
    protected int pathCount;
    protected int kStepsCount;
    
    public void setStartPoint(int point) {
        this.startPoint = point;
    }
    
    public double getAveragePathSize() {
        if (super.passedReplications > 0) {
        return this.pathCount / (double) super.passedReplications;
        } else {
            return 0.0;
        }
    }
    
    public void updatePathCount() {
        this.pathCount += this.path.size();
        if (this.path.size() > super.getKSteps()) {
            this.kStepsCount++;
        }
    }
    
    public double getKStepsProbability() {
        if (super.passedReplications > 0) {
        return (this.kStepsCount / (double) super.passedReplications) * 100;
        } else {
            return 0.0;
        }
    }
    
    public int calculatePosition(int x, int y) {
        return x * super.xSize + y + 1;
    }
    
    public int[] getMatrixPosition(int point) {
        int[] position = new int[2];
        position[0] = (int) Math.floor((point - 1) / (double) super.xSize);
        position[1] = (point - 1) - position[0] * super.xSize;
        return position;
    }
    
    public ArrayList<Integer> getNextPoints() {
        ArrayList<Integer> neighbors = new ArrayList<>();
        int currentPosition = this.path.get(this.path.size() - 1);
        int[] matrixPosition = getMatrixPosition(currentPosition);
        int positionX = matrixPosition[0];
        int positionY = matrixPosition[1];
        
        int lastPositionFrom = (-1 * Integer.MAX_VALUE / 2);
        if (this.path.size() > 1) {
            lastPositionFrom = this.path.get(this.path.size() - 2);
        }
        
        if (positionX > 0) {
            int next = calculatePosition(positionX - 1, positionY);
            if (lastPositionFrom != next) {
                neighbors.add(next);
            }
        }
        if (positionX < super.xSize - 1) {
            int next = calculatePosition(positionX + 1, positionY);
            if (lastPositionFrom != next) {
                neighbors.add(next);
            }
        }
        if (positionY > 0) {
            int next = calculatePosition(positionX, positionY - 1);
            if (lastPositionFrom != next) {
                neighbors.add(next);
            }
        }
        if (positionY < super.ySize - 1) {
            int next = calculatePosition(positionX, positionY + 1);
            if (lastPositionFrom != next) {
                neighbors.add(next);
            }
        }
        return neighbors;
    }
    
    public int getNextPoint() {
        int travelTo = 0;
        
        int currentPosition = this.path.get(this.path.size() - 1);
        int[] matrixPosition = getMatrixPosition(currentPosition);
        int positionX = matrixPosition[0];
        int positionY = matrixPosition[1];
        
        boolean right = true;
        boolean down = true;
        boolean left = true;
        boolean up = true;
        
        while ((right || down || left || up) && !this.collision) {
            if (positionY < super.ySize - 1 && right) {
                int next = calculatePosition(positionX, positionY + 1);
                if (!this.path.contains(next)) {
                    travelTo = next;
                    break;
                } else {
                    right = false;
                }
            } else if (positionX < super.xSize - 1 && down) {
                int next = calculatePosition(positionX + 1, positionY);
                if (!this.path.contains(next)) {
                    travelTo = next;
                    break;
                } else {
                    down = false;
                }
            } else if (positionY > 0 && left) {
                int next = calculatePosition(positionX, positionY - 1);
                if (!this.path.contains(next)) {
                    travelTo = next;
                    break;
                } else {
                    left = false;
                }
            } else if (positionX > 0 && up) {
                int next = calculatePosition(positionX - 1, positionY);
                if (!this.path.contains(next)) {
                    travelTo = next;
                    break;
                } else {
                    up = false;
                }
            } else {
                this.collision = true;
            }
        }
        
        if (travelTo >= (super.xSize * super.ySize) || travelTo <= 0) {
            travelTo = 0;
            this.collision = true;
        }
        
        return travelTo;
    }
    
    public void move(int toPoint) {
        if (!this.path.contains(toPoint)) {
            this.path.add(toPoint);
//            System.out.println(pathToString());
        } else {
            this.collision = true;
//            System.out.println("Collision to point " + toPoint);
        }
    }
    
    public void setCollision(boolean collision) {
        this.collision = collision;
    }
    
    public boolean getCollision() {
        return this.collision;
    }
    
    public String pathToString() {
        if (this.path.size() > 0) {
            String path = "" + this.path.get(0);
            for (int i = 0; i < this.path.size() - 1; i++) {
                int dif = this.path.get(i) - this.path.get(i + 1);
                if (dif == 1) {
                    path += " ← ";
                } else if (dif == -1) {
                    path += " → ";
                } else if (dif < -1) {
                    path += " ↓ ";
                } else if (dif > 1) {
                    path += " ↑ ";
                }
                path += "" + this.path.get(i + 1);
            }
            return path + ", Count = " + this.path.size() + "\n";
        } else {
            return "Path is empty!";
        }
    }
    
    public void resetRobot() {
        this.path = new ArrayList<>();
        this.path.add(this.startPoint);
        this.collision = false;
    }
    
    public void resetCountHistory() {
        this.pathCount = 0;
        this.kStepsCount = 0;
    }
}
