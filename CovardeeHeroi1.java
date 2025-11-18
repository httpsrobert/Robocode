package CovardeeHeroi;

import robocode.*;
import robocode.util.Utils;
import java.awt.Color;

public class CovardeeHeroi1 extends AdvancedRobot {

    @Override
    public void run() {

      
        setBodyColor(new Color(128, 0, 128));
        setGunColor(new Color(0, 255, 0));
        setRadarColor(Color.WHITE); 
        setBulletColor(Color.GREEN); 
        setScanColor(Color.MAGENTA); 

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        // Radar infinito igual ao Tracker
        setTurnRadarRight(Double.POSITIVE_INFINITY);

        while (true) {
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {

        int vivos = getOthers();
        double distancia = e.getDistance();

        double radarTurn =
                getHeading() - getRadarHeading() + e.getBearing();
        setTurnRadarRight(Utils.normalRelativeAngleDegrees(radarTurn));

        // aqui é pra mantar uma distancia de 300 und
        double move = 300 - distancia; 
        setAhead(-move);               

        // movimento aleatorio
        setTurnRight(30 - (Math.random() * 60));

        double turn =
                getHeading() - getGunHeading() + e.getBearing();
        setTurnGunRight(Utils.normalRelativeAngleDegrees(turn));

        // atirar apenas quando restar 5 pra sobrar energia
        if (vivos <= 5) {

            if (distancia < 250) {
                fire(2);
            } else {
                fire(1);
            }
        }
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {

        // fugir quando toma bala
        double ang = Utils.normalRelativeAngleDegrees(e.getBearing() + 180);
        setTurnRight(ang);
        setAhead(250);
    }
}