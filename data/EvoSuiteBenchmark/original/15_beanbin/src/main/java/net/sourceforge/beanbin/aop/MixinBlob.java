package net.sourceforge.beanbin.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.codegen.BlobClassMaker;
import net.sourceforge.beanbin.data.EntityUtils;

import org.jboss.aop.AspectManager;
import org.jboss.aop.introduction.AnnotationIntroduction;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.introduction.InterfaceIntroduction.Mixin;

public class MixinBlob {
	public void mix(Class clazz) throws BeanBinException {
		BlobClassMaker classMaker = new BlobClassMaker(clazz);		
		Class iface = classMaker.getInterfaceClass();
		Class mixinClass = classMaker.getMixinClass();
		if(needsMixin(clazz, iface)) {
			addMixin(clazz, iface, mixinClass);
			for(Method getter : EntityUtils.getBlobGetters(clazz)) {
				AnnotationIntroduction intro = AnnotationIntroduction.createMethodAnnotationIntroduction("* *->" + getter.getName() + "()", "@javax.persistence.Transient", false);
				AspectManager.instance().addAnnotationIntroduction(intro);
			}
		}
	}

	private void addMixin(Class clazz, Class iface, Class mixinClass) {
		String[] ifaces = {iface.getName()};		
		InterfaceIntroduction intro = new InterfaceIntroduction();		
		intro.setClassExpression(clazz.getName());
		Mixin mixin = new Mixin();
		mixin.setClassname(mixinClass.getName());
		mixin.setInterfaces(ifaces);
		mixin.setConstruction(mixinClass.getName() + "(this)");
		ArrayList<Mixin> mixins = new ArrayList<Mixin>();
		intro.setMixins(mixins);
		
		AspectManager.instance().addInterfaceIntroduction(intro);
	}
	
	private boolean needsMixin(Class clazz, Class iface) throws BeanBinException {
		try {
			iface.cast(clazz.newInstance());
			return false;
		} catch(ClassCastException e) {
			return true;
		} catch(Exception e) {
			throw new BeanBinException("Can't construct " + clazz.getName(), e);
		}
	}
}
