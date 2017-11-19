package speakerrecognition.pojos;

public class MfccParameters {
	
	private double[][] mfccCoeffs;

	public MfccParameters(double[][] mfccCoeffs) {
		super();
		this.mfccCoeffs = mfccCoeffs;
	}

	public double[][] getMfccCoefs() {
		return mfccCoeffs;
	}

	public void setMfccCoefs(double[][] mfccCoefs) {
		this.mfccCoeffs = mfccCoefs;
	}

}
