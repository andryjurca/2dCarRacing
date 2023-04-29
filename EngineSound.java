import javax.sound.sampled.*;

public class EngineSound implements Runnable {

    private static final int SAMPLE_RATE = 44100; // Samples per second
    private static final int BUFFER_SIZE = 1024; // Bytes
    private static final double A_FREQUENCY = 440.0;

    private final double frequency;
    private final long durationMillis;
    private final float volume;

    public EngineSound(double frequency, long durationMillis, float volume) {
        this.frequency = frequency;
        this.durationMillis = durationMillis;
        this.volume = volume;
    }

    public static double rpmToFrequency(double rpm) {
        // Calculate the frequency using a logarithmic scaling
        double minFrequency = 100.0;
        double maxFrequency = 150.0;
        double scalingFactor = Math.log(maxFrequency / minFrequency) / 7000.0;
        double frequency = 0.0;
        if (rpm > 0) {
            frequency = minFrequency * Math.exp(scalingFactor * rpm);
        }

        // Limit the frequency to the range [minFrequency, maxFrequency]
        frequency = Math.max(minFrequency, Math.min(maxFrequency, frequency));

        if (rpm == 0) {
            frequency = 0.0;
        }

        return frequency;
    }

    public static void playEngineSound(int rpm) {
        //double frequency = 100.0 * Math.exp((Math.log(2) / 3000.0) * (rpm - 6000));
        double frequency = rpmToFrequency(rpm);
        long duration = 100;
        float volume = 0.01f;
        EngineSound player = new EngineSound(frequency, duration, volume);
        new Thread(player).start();
    }

    private static byte[] createBuffer(double frequency, long durationMillis, float volume) {
        int numSamples = (int) (durationMillis * SAMPLE_RATE / 1000);
        byte[] buffer = new byte[numSamples * 2]; // Two bytes per sample
        for (int i = 0; i < numSamples; i++) {
            double angle = i / (SAMPLE_RATE / frequency) * 2.0 * Math.PI;
            short sample = (short) (Math.sin(angle) * volume * Short.MAX_VALUE);
            buffer[i * 2] = (byte) (sample & 0xff);
            buffer[i * 2 + 1] = (byte) (sample >> 8);
        }
        return buffer;
    }

    @Override
    public void run() {
        try {
            AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
            SourceDataLine line = AudioSystem.getSourceDataLine(format);
            line.open(format);
            line.start();
            byte[] buffer = createBuffer(frequency, durationMillis, volume);
            line.write(buffer, 0, buffer.length);
            line.drain();
            line.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int rpm = 0;

        while (true) {
            double frequency = A_FREQUENCY * Math.pow(2, (rpm - 6000) / 1200.0);
            long duration = 100;
            float volume = 0.5f;
            EngineSound player = new EngineSound(frequency, duration, volume);
            new Thread(player).start();
            rpm += 10;
            System.out.println(rpm);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
