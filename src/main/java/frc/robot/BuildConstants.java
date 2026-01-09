package frc.robot;

/**
 * Build metadata populated at compile time. If Gradle fails to generate this class automatically,
 * the fallback values below prevent compile failures while still indicating that metadata may be
 * unavailable. Update these values manually if you need more accurate information when building
 * outside of the standard GradleRIO pipeline.
 */
public final class BuildConstants {
    private BuildConstants() {}

    public static final String MAVEN_GROUP = "frc.robot";
    public static final String MAVEN_NAME = "SaturnReach";
    public static final String VERSION = "unspecified";

    public static final String GIT_SHA = "UNKNOWN";
    public static final String GIT_DATE = "UNKNOWN";
    public static final String GIT_BRANCH = "UNKNOWN";
    public static final String BUILD_DATE = "UNKNOWN";
    public static final long BUILD_UNIX_TIME = 0L;
    /** 0 = clean, 1 = dirty, 2 = unknown */
    public static final int DIRTY = 2;
}
