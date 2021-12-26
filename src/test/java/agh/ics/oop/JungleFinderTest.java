package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class JungleFinderTest {
    @Test
    public void jungleFinderTest0() {
        WrappedMap map = new WrappedMap(3, 3, 1.0 / 8.0);
        System.out.println(map.getJungleSize()[0] + " " + map.getJungleSize()[1]);
        /*assertEquals(new Vector2d(1, 1), map.getJungleSize()[0],
                "lower left error0");
        assertEquals(new Vector2d(1, 1), map.getJungleSize()[1],
                "upper right error0");*/
    }

    @Test
    public void jungleFinderTest1() {
        WrappedMap map = new WrappedMap(5, 5, 9.0 / 16.0);
        assertEquals(new Vector2d(1, 1), map.getJungleSize()[0],
                "lower left error1");
        assertEquals(new Vector2d(3, 3), map.getJungleSize()[1],
                "upper right error1");
    }

    @Test
    public void randomJungleFinderTest() {
        double maxDiff = -10e9;
        for(int i = 0; i < 10e6 * 5; i++) {
            int width = new Random().nextInt(200) + 10;
            int height = new Random().nextInt(200) + 10;
            int tmp = new Random().nextInt(10) + 1;
            double jungleRatio = (double) tmp / (double) (tmp + new Random().nextInt(20));
            RectangularMap map = new RectangularMap(width, height, jungleRatio);
            Vector2d[] jungleSize = map.getJungleSize();
            int jungleArea = (jungleSize[1].x - jungleSize[0].x + 1) * (jungleSize[1].y - jungleSize[0].y + 1);
            maxDiff = Math.max(maxDiff, Math.abs((jungleArea) / (double) (height * width - jungleArea) - jungleRatio));

            assertTrue(Math.abs(jungleSize[0].x - (width - 1 - jungleSize[1].x)) <= 1 && Math.abs(jungleSize[0].y - (height - 1 - jungleSize[1].y)) <= 1, "wrong jungle placement");

            assertTrue(Math.abs((jungleArea) / (double) (height * width - jungleArea) - jungleRatio) < 0.15, "ratio too inaccurate, ratio achieved = " +
                        (jungleArea) / (double) (height * width - jungleArea) + " ratio expected = " + jungleRatio +
                        " difference = " + Math.abs((jungleArea) / (double) (height * width - jungleArea) - jungleRatio) +
                        " jungle size: " + jungleSize[0] + jungleSize[1] + " " + width + " " + height);


        }
    }
}
