import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class Cyberpunk extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Cyberpunk.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
        noiseSeed(floor(random(MAX_INT)));
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        noFill();
        noLoop();
    }

    @Override
    public void draw() {
        for (int x = MARGIN; x < WIDTH - MARGIN; x += STEP) {
            for (int y = MARGIN; y < HEIGHT - MARGIN; y += STEP) {
                if (random(1f) < 0.5) {
                    stroke(STROKE_COLOR_1.red(), STROKE_COLOR_1.green(), STROKE_COLOR_1.blue(), STROKE_COLOR_1.alpha());
                } else {
                    stroke(STROKE_COLOR_2.red(), STROKE_COLOR_2.green(), STROKE_COLOR_2.blue(), STROKE_COLOR_2.alpha());
                }
                PVector p = new PVector(x, y);
                for (int l = 0; l < MAX_LINE_LENGTH; l++) {
                    point(p.x, p.y);
                    PVector f = PVector.sub(p, new PVector(width / 2f, height / 2f)).rotate(HALF_PI).normalize();
                    f.add(PVector.fromAngle(TWO_PI * (dist(p.x, p.y, width / 2f, height / 2f) / width
                            + myNoise(p.x, p.y))).mult(NOISE_VELOCITY));
                    float angle = floor(NUMBER_OF_ANGLES * f.heading() / TWO_PI) / (float) NUMBER_OF_ANGLES * TWO_PI;
                    p.add(PVector.fromAngle(angle).setMag(NOISE_FORCE * f.mag()));
                    if (p.x < 0 || p.x > width || p.y < 0 || p.y > height) {
                        break;
                    }
                }
            }
        }

        saveSketch(this);
    }

    private float myNoise(float x, float y) {
        return noise(x * NOISE_SCALE
                        + sin(NOISE_ANGULAR_MULTIPLIER * TWO_PI
                        * noise(x * NOISE_SCALE * NOISE_INNER_MULTIPLIER, y * NOISE_SCALE * NOISE_INNER_MULTIPLIER)),
                y * NOISE_SCALE
                        + cos(NOISE_ANGULAR_MULTIPLIER * TWO_PI
                        * noise(x * NOISE_SCALE * NOISE_INNER_MULTIPLIER, y * NOISE_SCALE * NOISE_INNER_MULTIPLIER)));
    }
}
