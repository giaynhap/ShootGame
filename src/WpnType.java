
public enum WpnType {
	LeftHand(1),DualHand(3),LeftHand2(5),RightHand(2),DualHand2(4),RightHand2(6);

    private final int value;
    private WpnType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
