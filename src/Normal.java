import javax.vecmath.Vector3f;

public class Normal {

	public int n;
	public Vector3f normalX;
	public Vector3f normalY;
	
	Normal(int n, Vector3f v1, Vector3f v2) {
		this.n = n;
		this.normalX = v1;
		this.normalY = v2;
	}
	
	public int getN() {
		return this.n;
	}
	
	public Vector3f getVecteurV1() {
		return this.normalX;
	}
	
	public Vector3f getVecteurV2() {
		return this.normalY;
	}
}
