package br.UFSC.GRIMA.j3d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.sql.Ref;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.operationSolids.CSGSolid;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class J3D {
	private SimpleUniverse univ = null;
	//protected BranchGroup solidsBG = new BranchGroup();
	protected BranchGroup solidsBG = null;
	//private BranchGroup solidBG = new BranchGroup();
	public TransformGroup objTrans = null;
	//public TransformGroup objTrans =  new TransformGroup();
	Canvas3D c;

	public J3D(JPanel p1) {

		GraphicsDevice screenDevice = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();

		GraphicsConfiguration gc = screenDevice.getBestConfiguration(template);
		this.c = new Canvas3D(gc);

		p1.add(this.c, BorderLayout.CENTER);
		this.solidsBG = createSceneGraph(this.c);
		this.univ = new SimpleUniverse(this.c);
		Transform3D viewerTranslation = new Transform3D();
		viewerTranslation.setTranslation(new Vector3f(0, 0, 15));
		this.univ.getViewingPlatform().getViewPlatformTransform().setTransform(
				viewerTranslation);
		this.solidsBG.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		this.solidsBG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		this.solidsBG.compile();
		this.univ.addBranchGraph(solidsBG);
	}

	public void removeSolid() 
	{
		solidsBG.removeAllChildren();
	}
	public void addSolid(CSGSolid solid) {
		
		BranchGroup solidBG = new BranchGroup();
		objTrans = new TransformGroup();
		objTrans.setCapability(objTrans.ALLOW_TRANSFORM_READ);
		objTrans.setCapability(objTrans.ALLOW_TRANSFORM_WRITE);
		
		//Reference reference = new Reference(bloco);
		//objTrans.addChild(reference.createTextX());
		//objTrans.addChild(reference.createTextY());
		//objTrans.addChild(reference.createTextZ());
		
		Transform3D t3d = new Transform3D();
		t3d.setScale(0.005);
		objTrans.setTransform(t3d);
		solidBG.addChild(objTrans);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0.0, 0.0), 100.0);
		MouseRotate behavior = new MouseRotate();
		behavior.setTransformGroup(objTrans);
		objTrans.addChild(behavior);
		behavior.setSchedulingBounds(bounds);
		MouseTranslate tras = new MouseTranslate();
		tras.setTransformGroup(objTrans);
		objTrans.addChild(tras);
		tras.setSchedulingBounds(bounds);
		MouseZoom zm = new MouseZoom();
		zm.setTransformGroup(objTrans);
		objTrans.addChild(zm);
		zm.setSchedulingBounds(bounds);
	
		objTrans.addChild(solid);
		solidBG.setCapability(BranchGroup.ALLOW_DETACH);
		solidBG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		solidBG.compile();
		solidsBG.addChild(solidBG);
	}
	private BranchGroup createSceneGraph(Canvas3D canvas3d) {
		BranchGroup objRoot = new BranchGroup();

		// set branch group where solids are set
		solidsBG = new BranchGroup();
		solidsBG.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		solidsBG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		solidsBG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		solidsBG.setCapability(BranchGroup.ALLOW_DETACH);
		objRoot.addChild(solidsBG);

		BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 50);
		DirectionalLight lightD = new DirectionalLight();
		lightD.setDirection(new Vector3f(0.0f, -1, -3.0f));
		lightD.setInfluencingBounds(bounds);
		objRoot.addChild(lightD);
		AmbientLight lightA = new AmbientLight();
		lightA.setInfluencingBounds(bounds);
		lightA.setColor(new Color3f(0.5f, 0.5f, 0.5f));
		objRoot.addChild(lightA);

		// set the background 
		Background bg = new Background(new Color3f(0.f, 0.f, 0.0f));
		bg.setApplicationBounds(bounds);
		objRoot.addChild(bg);
		
		return objRoot;
	}
	private void addLight(TransformGroup objTrans) {
		// light
		BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);
		DirectionalLight lightD = new DirectionalLight();

		lightD.setDirection(new Vector3f(0.3f, -5, -0.9f));

		lightD.setInfluencingBounds(bounds);
		objTrans.addChild(lightD);
		AmbientLight lightA = new AmbientLight();
		lightA.setInfluencingBounds(bounds);
		lightA.setColor(new Color3f(Color.lightGray));
		objTrans.addChild(lightA);
	}
	private Canvas3D createUniverse() {

		GraphicsDevice screenDevice = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();
		GraphicsConfiguration gc = screenDevice.getBestConfiguration(template);
		Canvas3D c = new Canvas3D(gc);
		// Create simple universe with view branch
		univ = new SimpleUniverse(c);

		// This will move the ViewPlatform back a bit so the
		// objects in the scene can be viewed.
		univ.getViewingPlatform().setNominalViewingTransform();

		// Ensure at least 5 msec per frame (i.e., < 200Hz)
		univ.getViewer().getView().setMinimumFrameCycleTime(5);

		return c;
	}
	public void setTransformGroup(Primitive primitive) {
		BranchGroup branchGroup = new BranchGroup();
		// objTrans = new TransformGroup();
		objTrans.setCapability(objTrans.ALLOW_TRANSFORM_READ);
		objTrans.setCapability(objTrans.ALLOW_TRANSFORM_WRITE);

		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(1, 1, 1));
		objTrans.setTransform(t3d);

		BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0.0, 0.0),
				100.0);
		primitive.setBounds(bounds);

		MouseRotate behavior = new MouseRotate();
		behavior.setTransformGroup(objTrans);
		objTrans.addChild(behavior);
		behavior.setSchedulingBounds(bounds);

		MouseTranslate tras = new MouseTranslate();
		tras.setTransformGroup(objTrans);
		objTrans.addChild(tras);
		tras.setSchedulingBounds(bounds);

		MouseZoom zm = new MouseZoom();
		zm.setTransformGroup(objTrans);
		objTrans.addChild(zm);
		zm.setSchedulingBounds(bounds);
		branchGroup.addChild(objTrans);

		objTrans.addChild(primitive);

		branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
		branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		branchGroup.compile();
		
		solidsBG.addChild(branchGroup);
	}
	
	public void setLetters(TransformGroup tg, Vector3d position, double scale)
	{
		BranchGroup solidBG = new BranchGroup();
		//objTrans = new TransformGroup();
		//objTrans.setCapability(objTrans.ALLOW_TRANSFORM_READ);
		//objTrans.setCapability(objTrans.ALLOW_TRANSFORM_WRITE);

		//Transform3D t3d = new Transform3D();
		//t3d.setScale(0.05);
		//t3d.setTranslation(position);
		//t3d.setScale(1);
		//objTrans.setTransform(t3d);

		/*BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0.0, 0.0), 100.0);
		tg.setBounds(bounds);

		MouseRotate behavior = new MouseRotate();
		behavior.setTransformGroup(objTrans);
		objTrans.addChild(behavior);
		behavior.setSchedulingBounds(bounds);

		MouseTranslate tras = new MouseTranslate();
		tras.setTransformGroup(objTrans);
		objTrans.addChild(tras);
		tras.setSchedulingBounds(bounds);

		MouseZoom zm = new MouseZoom();
		zm.setTransformGroup(objTrans);
		objTrans.addChild(zm);
		zm.setSchedulingBounds(bounds);
		branchGroup.addChild(objTrans);*/

		objTrans.addChild(tg);

		solidBG.setCapability(BranchGroup.ALLOW_DETACH);
		solidBG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		//solidBG.compile();
		solidsBG.addChild(solidBG);
	}
}
