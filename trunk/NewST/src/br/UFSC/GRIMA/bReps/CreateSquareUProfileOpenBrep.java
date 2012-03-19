package br.UFSC.GRIMA.bReps;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

public class CreateSquareUProfileOpenBrep {
	public Point3d[] vertexArray;
	public int[] indexArray;
	public Color3f[] color3f;
	private int numberOfPointsInArc = 4;
	private float d1, width, depth, radius0, radius1, angle0, angle1;
	private String name;

	public CreateSquareUProfileOpenBrep(String name, float d1, float width,
			float depth, float radius0, float radius1, float angle0,
			float angle1) {
		this.name = name;
		this.d1 = d1;
		this.width = width;
		this.depth = depth;
		this.radius0 = radius0;
		this.radius1 = radius1;
		this.angle0 = angle0;
		this.angle1 = angle1;
		this.generateVertexArray();
		this.generateIndexArray();
		this.generateColorArray();
	}

	private void generateColorArray() {
		color3f = new Color3f[vertexArray.length];
		for (int i = 0; i < color3f.length; i++) {
			color3f[i] = new Color3f(0.3f, 0.3f, 0.3f);
		}
	}

	private void generateVertexArray() {
		vertexArray = new Point3d[(numberOfPointsInArc * 2 + 3) * 2];
		Point3d[] arc0 = new Point3d[numberOfPointsInArc];
		Point3d[] arc1 = new Point3d[numberOfPointsInArc];

		vertexArray[0] = new Point3d(0, 0, 0);
		vertexArray[1] = new Point3d(width / 2 + depth
				* Math.tan(angle0 * Math.PI / 180), 0, 0);
		arc0 = determinatePointsInArcCircunference(-(angle0 * Math.PI / 180),
				-(90 - angle0) * Math.PI / 180, radius0, numberOfPointsInArc, 0);
		arc1 = determinatePointsInArcCircunference(-Math.PI / 2, -(90 - angle1)
				* Math.PI / 180, radius1, numberOfPointsInArc, 0);
		for (int i = 0; i < arc0.length; i++) {
			vertexArray[i + 2] = new Point3d(arc0[i].x + width / 2 - radius0
					* Math.tan(((90 - angle0) / 2) * Math.PI / 180), -depth
					+ radius0 + arc0[i].y, 0);
			vertexArray[i + 2 + arc0.length] = new Point3d(
					-width / 2 + radius1
							* Math.tan(((90 - angle1) / 2) * Math.PI / 180)
							+ arc1[i].x, -depth + radius1 + arc1[i].y, 0);
		}

		// last point in front profile
		vertexArray[vertexArray.length / 2 - 1] = new Point3d(-width / 2
				- depth * Math.tan(angle1 * Math.PI / 180), 0, 0);

		// points in rear profile
		for (int i = 0; i < vertexArray.length / 2; i++) {
			vertexArray[vertexArray.length / 2 + i] = new Point3d(
					vertexArray[i].x, vertexArray[i].y, -d1);
		}

		// verification
		/*
		 * for (int i = 0; i < vertexArray.length; i++) { System.out.println(i +
		 * " vertexArray " + vertexArray[i]); }
		 */

	}

	private void generateIndexArray() {
		indexArray = new int[(numberOfPointsInArc * 2 + 1) * 3 * 2
				+ (vertexArray.length * 3)];

		for (int i = 0; i < vertexArray.length / 2 - 2; i++) {
			// index in front profile
			indexArray[i * 3] = 0;
			indexArray[i * 3 + 2] = i + 1;
			indexArray[i * 3 + 1] = i + 2;

			// index in rear profile
			indexArray[(numberOfPointsInArc * 2 + 1) * 3 + (3 * i)] = vertexArray.length / 2;
			indexArray[(numberOfPointsInArc * 2 + 1) * 3 + (3 * i + 1)] = vertexArray.length
					/ 2 + i + 1;
			indexArray[(numberOfPointsInArc * 2 + 1) * 3 + (3 * i + 2)] = vertexArray.length
					/ 2 + i + 2;
		}

		// index in side bounds
		for (int i = 0; i < vertexArray.length / 2 - 1; i++) {
			indexArray[(numberOfPointsInArc * 2 + 1) * 3 * 2 + 3 * i] = i;
			indexArray[(numberOfPointsInArc * 2 + 1) * 3 * 2 + 3 * i + 1] = i + 1;
			indexArray[(numberOfPointsInArc * 2 + 1) * 3 * 2 + 3 * i + 2] = vertexArray.length
					/ 2 + i;

			indexArray[(numberOfPointsInArc * 2 + 1) * 3 * 2
					+ vertexArray.length / 2 * 3 + 3 * i] = i + 1;
			indexArray[(numberOfPointsInArc * 2 + 1) * 3 * 2
					+ vertexArray.length / 2 * 3 + 3 * i + 1] = vertexArray.length
					/ 2 + 1 + i;
			indexArray[(numberOfPointsInArc * 2 + 1) * 3 * 2
					+ vertexArray.length / 2 * 3 + 3 * i + 2] = vertexArray.length
					/ 2 + i;
		}

		// special triangles in side bounds
		indexArray[(numberOfPointsInArc * 2 + 1) * 6 + vertexArray.length / 2
				* 3 - 3] = 0;
		indexArray[(numberOfPointsInArc * 2 + 1) * 6 + vertexArray.length / 2
				* 3 - 2] = vertexArray.length - 1;
		indexArray[(numberOfPointsInArc * 2 + 1) * 6 + vertexArray.length / 2
				* 3 - 1] = vertexArray.length / 2 - 1;

		indexArray[indexArray.length - 3] = 0;
		indexArray[indexArray.length - 2] = vertexArray.length / 2;
		indexArray[indexArray.length - 1] = vertexArray.length - 1;

		// verification
		/*
		 * for (int i = 0; i < indexArray.length; i++) { System.out.println(i +
		 * " indexArray " + indexArray[i]); }
		 */
	}

	private Point3d[] determinatePointsInArcCircunference(double initialAngle,
			double deltaAngle, double radius, int numberOfPoints, double zPlane) {
		Point3d[] output = new Point3d[numberOfPoints];
		double x, y;
		double dAngle = 0;
		dAngle = deltaAngle / (numberOfPoints - 1);
		for (int i = 0; i < numberOfPoints; i++) {
			x = radius * Math.cos(initialAngle + i * dAngle);
			y = radius * Math.sin(initialAngle + i * dAngle);
			output[i] = new Point3d((float) x, (float) y, (float) zPlane);
			// System.out.println(i + " x z:" + output[i].getX() + " " +
			// output[i].getZ());
		}
		return output;
	}
}
