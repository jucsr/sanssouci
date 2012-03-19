package br.UFSC.GRIMA.j3d;

import java.awt.Font;

import javax.media.j3d.Appearance;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import br.UFSC.GRIMA.entidades.features.Bloco;

public class Reference {
	private Bloco bloco;

	public Reference(Bloco bloco)
	{
		this.bloco = bloco;
	}
	public TransformGroup createTextX() 
	{

		Appearance textAppear = new Appearance();
		textAppear.setMaterial(new Material());

		Font3D font3D = new Font3D(new Font("Helvetica", Font.PLAIN, 1),
				new FontExtrusion());

		Text3D textGeom = new Text3D(font3D, new String("X"));
		textGeom.setAlignment(Text3D.ALIGN_CENTER);
		Shape3D textShape = new Shape3D();
		textShape.setGeometry(textGeom);
		textShape.setAppearance(textAppear);
		Transform3D XTransform3D = new Transform3D();
		//XTransform3D.setScale((bloco.getComprimento() + bloco.getLargura() + bloco.getProfundidade())/10);
		//XTransform3D.setTranslation(new Vector3f((float)-bloco.getComprimento()/2, (float)bloco.getProfundidade()/2, (float)bloco.getLargura()/2));
		XTransform3D.setTranslation(new Vector3d(-2.0 * bloco.getComprimento(), 5 * bloco.getProfundidade()/2, 5 * bloco.getLargura()/2));
		
		TransformGroup XTrans = new TransformGroup(XTransform3D);
		XTrans.setCapability(XTrans.ALLOW_TRANSFORM_READ);
		XTrans.setCapability(XTrans.ALLOW_TRANSFORM_WRITE);
		XTrans.addChild(textShape);
		return XTrans;
	}

	public TransformGroup createTextY() 
	{
		Appearance textAppear = new Appearance();
		textAppear.setMaterial(new Material());
		Font3D font3D = new Font3D(new Font("Helvetica", Font.PLAIN, 1),
				new FontExtrusion());
		Text3D textGeom = new Text3D(font3D, new String("Y"));
		textGeom.setAlignment(Text3D.ALIGN_CENTER);
		Shape3D textShape = new Shape3D();
		textShape.setGeometry(textGeom);
		textShape.setAppearance(textAppear);
		Transform3D YTransform3D = new Transform3D();
		//YTransform3D.setTranslation(new Vector3d(-2.5 * bloco.getComprimento(), 8.5 * bloco.getProfundidade()/2, 5 * bloco.getLargura()/2));
		//YTransform3D.setScale((bloco.getComprimento() + bloco.getLargura() + bloco.getProfundidade())/10);
		YTransform3D.setTranslation(new Vector3d(2.5*(-bloco.getComprimento()/2), 5*bloco.getProfundidade()/2, 5*bloco.getLargura()/2));
		YTransform3D.setScale(10);

		TransformGroup YTrans = new TransformGroup(YTransform3D);
		YTrans.setCapability(YTrans.ALLOW_TRANSFORM_READ);
		YTrans.setCapability(YTrans.ALLOW_TRANSFORM_WRITE);
		YTrans.addChild(textShape);
		return YTrans;
	}

	public TransformGroup createTextZ() {
		Appearance textAppear = new Appearance();
		textAppear.setMaterial(new Material());
		Font3D font3D = new Font3D(new Font("Helvetica", Font.PLAIN, 1),
				new FontExtrusion());
		Text3D textGeom = new Text3D(font3D, new String("Z"));
		textGeom.setAlignment(Text3D.ALIGN_CENTER);
		Shape3D textShape = new Shape3D();
		textShape.setGeometry(textGeom);
		textShape.setAppearance(textAppear);
		Transform3D ZTransform3D = new Transform3D();

		TransformGroup ZTrans = new TransformGroup(ZTransform3D);
		ZTrans.setCapability(ZTrans.ALLOW_TRANSFORM_READ);
		ZTrans.setCapability(ZTrans.ALLOW_TRANSFORM_WRITE);
		ZTrans.addChild(textShape);
		return ZTrans;
	}
}
