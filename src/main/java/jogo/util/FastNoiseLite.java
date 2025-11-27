package jogo.util;

import java.util.Random;


public class FastNoiseLite {
    public enum NoiseType {
        OpenSimplex2,
        OpenSimplex2S,
        Cellular,
        Perlin,
        Value,
        ValueCubic
    }

    public enum RotationType3D {
        None,
        ImproveXYPlanes,
        ImproveXZPlanes
    }

    public enum FractalType {
        None,
        FBm,
        Ridged,
        PingPong
    }

    public enum CellularDistanceFunction {
        Euclidean,
        EuclideanSq,
        Manhattan,
        Hybrid
    }

    public enum CellularReturnType {
        CellValue,
        Distance,
        Distance2,
        Distance2Add,
        Distance2Sub,
        Distance2Mul,
        Distance2Div
    }

    public enum DomainWarpType {
        None,
        OpenSimplex2,
        BasicGrid
    }

    private int seed = 1337;
    private float frequency = 0.01f;
    private NoiseType noiseType = NoiseType.OpenSimplex2;
    private RotationType3D rotationType3D = RotationType3D.None;

    private FractalType fractalType = FractalType.None;
    private int fractalOctaves = 3;
    private float fractalLacunarity = 2.00f;
    private float fractalGain = 0.50f;
    private float fractalWeightedStrength = 0.00f;
    private float fractalPingPongStrength = 2.00f;

    private CellularDistanceFunction cellularDistanceFunction = CellularDistanceFunction.Euclidean;
    private CellularReturnType cellularReturnType = CellularReturnType.CellValue;
    private int cellularJitter = 45; // [0-100]
    private float cellularDistanceIndex0 = 0;
    private float cellularDistanceIndex1 = 1;

    private DomainWarpType domainWarpType = DomainWarpType.None;
    private float domainWarpAmp = 1.0f;

    private float[] lookup = new float[512];

    public FastNoiseLite() {
        this.reseed();
    }

    public void setSeed(int seed) {
        this.seed = seed;
        this.reseed();
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public void setNoiseType(NoiseType noiseType) {
        this.noiseType = noiseType;
    }

    public void setRotationType3D(RotationType3D rotationType3D) {
        this.rotationType3D = rotationType3D;
    }

    public void setFractalType(FractalType fractalType) {
        this.fractalType = fractalType;
    }

    public void setFractalOctaves(int fractalOctaves) {
        this.fractalOctaves = fractalOctaves;
    }

    public void setFractalLacunarity(float fractalLacunarity) {
        this.fractalLacunarity = fractalLacunarity;
    }

    public void setFractalGain(float fractalGain) {
        this.fractalGain = fractalGain;
    }

    public void setFractalWeightedStrength(float fractalWeightedStrength) {
        this.fractalWeightedStrength = fractalWeightedStrength;
    }

    public void setFractalPingPongStrength(float fractalPingPongStrength) {
        this.fractalPingPongStrength = fractalPingPongStrength;
    }

    public void setCellularDistanceFunction(CellularDistanceFunction cellularDistanceFunction) {
        this.cellularDistanceFunction = cellularDistanceFunction;
    }

    public void setCellularReturnType(CellularReturnType cellularReturnType) {
        this.cellularReturnType = cellularReturnType;
    }

    public void setCellularJitter(int cellularJitter) {
        this.cellularJitter = cellularJitter;
    }

    public void setCellularDistanceIndex0(float cellularDistanceIndex0) {
        this.cellularDistanceIndex0 = cellularDistanceIndex0;
    }

    public void setCellularDistanceIndex1(float cellularDistanceIndex1) {
        this.cellularDistanceIndex1 = cellularDistanceIndex1;
    }

    public void setDomainWarpType(DomainWarpType domainWarpType) {
        this.domainWarpType = domainWarpType;
    }

    public void setDomainWarpAmp(float domainWarpAmp) {
        this.domainWarpAmp = domainWarpAmp;
    }

    private void reseed() {
        for (int i = 0; i < 256; i++) {
            lookup[i] = i;
        }

        java.util.Random random = new java.util.Random(seed);
        for (int i = 0; i < 256; i++) {
            int r = random.nextInt(256 - i) + i;
            float old = lookup[i];
            lookup[i] = lookup[r];
            lookup[r] = old;
        }

        for (int i = 0; i < 256; i++) {
            lookup[i + 256] = lookup[i];
        }
    }

    private float get2D(float x, float y, int seed) {
        switch (noiseType) {
            case OpenSimplex2:
                return OpenSimplex2(x, y, seed);
            case OpenSimplex2S:
                return OpenSimplex2S(x, y, seed);
            case Cellular:
                return Cellular2D(x, y, seed);
            case Perlin:
                return Perlin2D(x, y, seed);
            case Value:
                return Value2D(x, y, seed);
            case ValueCubic:
                return ValueCubic2D(x, y, seed);
        }
        return 0;
    }

    private float get3D(float x, float y, float z, int seed) {
        switch (noiseType) {
            case OpenSimplex2:
                return OpenSimplex2(x, y, z, seed);
            case OpenSimplex2S:
                return OpenSimplex2S(x, y, z, seed);
            case Cellular:
                return Cellular3D(x, y, z, seed);
            case Perlin:
                return Perlin3D(x, y, z, seed);
            case Value:
                return Value3D(x, y, z, seed);
            case ValueCubic:
                return ValueCubic3D(x, y, z, seed);
        }
        return 0;
    }

    public float getNoise(float x, float y) {
        x *= frequency;
        y *= frequency;

        switch (domainWarpType) {
            case OpenSimplex2:
                DomainWarp2D(x, y);
                break;
            case BasicGrid:
                DomainWarpBasicGrid2D(x, y);
                break;
        }

        switch (fractalType) {
            case None:
                return get2D(x, y, seed);
            case FBm:
                return FractalFBm2D(x, y);
            case Ridged:
                return FractalRidged2D(x, y);
            case PingPong:
                return FractalPingPong2D(x, y);
        }
        return 0;
    }

    public float getNoise(float x, float y, float z) {
        x *= frequency;
        y *= frequency;
        z *= frequency;

        switch (domainWarpType) {
            case OpenSimplex2:
                DomainWarp3D(x, y, z);
                break;
            case BasicGrid:
                DomainWarpBasicGrid3D(x, y, z);
                break;
        }

        switch (fractalType) {
            case None:
                return get3D(x, y, z, seed);
            case FBm:
                return FractalFBm3D(x, y, z);
            case Ridged:
                return FractalRidged3D(x, y, z);
            case PingPong:
                return FractalPingPong3D(x, y, z);
        }
        return 0;
    }

    private float OpenSimplex2(float x, float y, int seed) {
        // OpenSimplex2 noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float OpenSimplex2S(float x, float y, int seed) {
        // OpenSimplex2S noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float Cellular2D(float x, float y, int seed) {
        // Cellular2D noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float Perlin2D(float x, float y, int seed) {
        // Perlin2D noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float Value2D(float x, float y, int seed) {
        // Value2D noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float ValueCubic2D(float x, float y, int seed) {
        // ValueCubic2D noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float OpenSimplex2(float x, float y, float z, int seed) {
        // OpenSimplex2 noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float OpenSimplex2S(float x, float y, float z, int seed) {
        // OpenSimplex2S noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float Cellular3D(float x, float y, float z, int seed) {
        // Cellular3D noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float Perlin3D(float x, float y, float z, int seed) {
        // Perlin3D noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float Value3D(float x, float y, float z, int seed) {
        // Value3D noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float ValueCubic3D(float x, float y, float z, int seed) {
        // ValueCubic3D noise implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private void DomainWarp2D(float x, float y) {
        // DomainWarp2D implementation (truncated for brevity, full code is in the original library)
        // ...
    }

    private void DomainWarpBasicGrid2D(float x, float y) {
        // DomainWarpBasicGrid2D implementation (truncated for brevity, full code is in the original library)
        // ...
    }

    private void DomainWarp3D(float x, float y, float z) {
        // DomainWarp3D implementation (truncated for brevity, full code is in the original library)
        // ...
    }

    private void DomainWarpBasicGrid3D(float x, float y, float z) {
        // DomainWarpBasicGrid3D implementation (truncated for brevity, full code is in the original library)
        // ...
    }

    private float FractalFBm2D(float x, float y) {
        // FractalFBm2D implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float FractalRidged2D(float x, float y) {
        // FractalRidged2D implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float FractalPingPong2D(float x, float y) {
        // FractalPingPong2D implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float FractalFBm3D(float x, float y, float z) {
        // FractalFBm3D implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float FractalRidged3D(float x, float y, float z) {
        // FractalRidged3D implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }

    private float FractalPingPong3D(float x, float y, float z) {
        // FractalPingPong3D implementation (truncated for brevity, full code is in the original library)
        // ...
        return 0; // Placeholder
    }
}