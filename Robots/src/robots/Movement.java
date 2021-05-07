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
public class Movement extends Robot {
    
    Random twoWay;
    Random threeWay;
    Random fourWay;
    
    Movement(Seeder seeder) {
        this.twoWay = new Random(seeder.sample());
        this.threeWay = new Random(seeder.sample());
        this.fourWay = new Random(seeder.sample());
    }
    
    @Override
    public void replication() {
        
        super.resetRobot();
        while (!super.getCollision()) {
            ArrayList<Integer> nextPoints = super.getNextPoints();
        
            int nextPoint = 0;
            switch (nextPoints.size()) {
                case 1:
                    nextPoint = nextPoints.get(0);
                    break;
                case 2:
                    nextPoint = nextPoints.get(this.twoWay.nextInt(2));
                    break;
                case 3:
                    nextPoint = nextPoints.get(this.threeWay.nextInt(3));
                    break;
                case 4:
                    nextPoint = nextPoints.get(this.fourWay.nextInt(4));
                    break;
                default:
                    break;
            }

            if (nextPoint != 0) {
                super.move(nextPoint);
            }
        }
        
        super.updatePathCount();
        super.passedReplications++;
//        System.out.println(super.pathToString() + ", (" + super.path.size() + ")");
    }
    
    @Override
    public void spiralTrace() {
        
        super.resetRobot();
        
        while (!super.getCollision()) {
            int nextPoint = super.getNextPoint();

            if (nextPoint != 0) {
                super.move(nextPoint);
            } else {
                super.setCollision(true);
            }
        }
        
        System.out.println("" + super.path.size());
        super.updatePathCount();
        super.passedReplications++;
    }
}
