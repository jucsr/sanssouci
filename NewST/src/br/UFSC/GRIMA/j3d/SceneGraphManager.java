package br.UFSC.GRIMA.j3d;

import java.util.Enumeration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import br.UFSC.GRIMA.operationSolids.CSGSolid;

import com.sun.j3d.utils.universe.SimpleUniverse;








public class SceneGraphManager {
	/** branch group where the solids are set */
	private BranchGroup solidsBG;
	/** behavior to be able to pick the solids */
	private GeneralPickBehavior pickBehavior;
	
	
	
	
	
	private BranchGroup createSceneGraph(SimpleUniverse simpleU, Canvas3D canvas3d, SolidsSelectionListener listener)
	{
		BranchGroup objRoot = new BranchGroup();
		
		// set branch group where solids are set
		solidsBG = new BranchGroup();
		solidsBG.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		solidsBG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		solidsBG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		objRoot.addChild(solidsBG);
				
		//picking
		pickBehavior = new GeneralPickBehavior(objRoot, canvas3d, listener);
		objRoot.addChild(pickBehavior);
				
		//light
		BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0),50);
		DirectionalLight lightD = new DirectionalLight();
		lightD.setDirection(new Vector3f(0.0f,-1,-3.0f));
		lightD.setInfluencingBounds(bounds);
		objRoot.addChild(lightD);
		AmbientLight lightA = new AmbientLight();
		lightA.setInfluencingBounds(bounds);
		lightA.setColor(new Color3f(0.3f, 0.3f, 0.3f));
		objRoot.addChild(lightA);
		
		return objRoot;
	}
	
	
}
