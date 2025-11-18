package CovardeeHeroi;

import robocode.*;
import robocode.util.Utils;
import java.awt.Color;

public class CovardeeHeroi2 extends AdvancedRobot {

    long ultimoScan = 0;
    String nomeDoCovarde = "CovardeeHeroi1";

    @Override
    public void run() {

      
        setBodyColor(Color.BLACK);
        setGunColor(Color.DARK_GRAY);
        setRadarColor(Color.YELLOW);
        setBulletColor(Color.YELLOW);
        setScanColor(Color.WHITE);

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        // Radar infinito para nunca parar
        setTurnRadarRight(Double.POSITIVE_INFINITY);

        while (true) {

            // sempre que nao achar outro robo a 8 tick de distancia ele se movimenta pra evitar ser atingido
            if (getTime() - ultimoScan > 8) {
                setAhead(120);
                setTurnRight(35);
            }

            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        ultimoScan = getTime();

        String nome = e.getName();

        if (nome.contains(nomeDoCovarde)) {
            // ignora o alidado e vira pro lado contrario
            double anguloInimigo = getHeading() + e.getBearing();
            double fuga = Utils.normalRelativeAngleDegrees(anguloInimigo - 180);
            setTurnRight(fuga - getHeading());
            setAhead(150);
            return;
        }

        // virar a arma pra atirar 
        double absolute = getHeading() + e.getBearing();
        double aim = Utils.normalRelativeAngleDegrees(absolute - getGunHeading());
        setTurnGunRight(aim);

        if (Math.abs(aim) < 10) {
            fire(2.2);
        }

        // 
        setAhead(180);
        setTurnRight(e.getBearing() / 2);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // desvia quando leva tiro e vai pro lado contrario
        double ang = Utils.normalRelativeAngleDegrees(e.getBearing() + 180);
        setTurnRight(ang);
        setAhead(150);
    }
}
