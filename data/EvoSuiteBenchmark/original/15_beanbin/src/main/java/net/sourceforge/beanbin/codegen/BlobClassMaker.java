package net.sourceforge.beanbin.codegen;

import java.lang.reflect.Method;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.EntityUtils;

public class BlobClassMaker {
	private Class interfaceClass;
	private Class mixinClass;
	
	public BlobClassMaker(Class entityClass) throws BeanBinException {
		initialize(entityClass);
	}
	
	public Class getInterfaceClass() {
		return interfaceClass;
	}
	
	public Class getMixinClass() {
		return mixinClass;
	}
	
	private void initialize(Class entityClass) throws BeanBinException {
		try {
			ClassPool pool = ClassPool.getDefault();
			String ifacename = entityClass.getName() + "BlobsInterface";
			String ccname = entityClass.getName() + "sBlobs";
			CtClass iface;
			List<Method> blobGetters = EntityUtils.getBlobGetters(entityClass);
			try {
				iface = pool.get(ifacename);
				this.interfaceClass = loadClass(ifacename);
			} catch(NotFoundException e) {
				iface = pool.makeInterface(ifacename);
				iface.stopPruning(true);
				for(Method getter : blobGetters) {
					makeMethod(iface, getGetterDefinition(getter) + ";");
					makeMethod(iface, getSetterDefinition(getter) + ";");
				}
				this.interfaceClass = iface.toClass();
			}
			
			CtClass cc;
			try {
				cc = pool.get(ccname);
				this.mixinClass = loadClass(ccname);								
			} catch(NotFoundException e) {
				cc = pool.get("net.sourceforge.beanbin.codegen.BlobClassTemplate");
				cc.setName(ccname);
				cc.stopPruning(true);
				CtClass[] ifaces = {iface};			
				cc.setInterfaces(ifaces);
				for(Method getter : blobGetters) {
					makeMethod(cc, makeGetter(getter));
					makeMethod(cc, makeSetter(getter));
				}
				this.mixinClass = cc.toClass();
			}
		} catch(Exception e) {
			if(e instanceof BeanBinException) {
				throw (BeanBinException)e;
			} else {
				throw new BeanBinException("BlobClassMaker.make: " + e.getMessage(), e);
			}
		}
	}

	private Class<?> loadClass(String className) throws ClassNotFoundException {
		return Thread.currentThread().getContextClassLoader().loadClass(className);
	}

	private CtMethod makeMethod(CtClass cc, String code) throws CannotCompileException {
		CtMethod method = CtNewMethod.make(code, cc);
		cc.addMethod(method);
		return method;
	}
	
	private String makeGetter(Method method) {
		String code = getGetterDefinition(method) + " {" +
					  "	return makeBlob(\"" + method.getName() + "\");" +
					  "}";
		
		return code;
	}
	
	private String getGetterDefinition(Method getter) {
		return "public java.sql.Blob " + getGetterName(getter) + "()";
	}

	private String getGetterName(Method getter) {
		return getter.getName() + "GeneratedBlob";
	}
	
	private String makeSetter(Method getter) {
		String code = getSetterDefinition(getter) + " {" +
					  "	setBlob(\"" + getter.getName() + "\", blob);" +
					  "}";
		
		return code;
	}
	
	private String getSetterDefinition(Method getter) {
		return "public void " + getSetterName(getter) + "(java.sql.Blob blob)";
	}

	private String getSetterName(Method getter) {
		String setterName = EntityUtils.getSetterName(getter);
		String name = setterName + "GeneratedBlob";
		return name;
	}
}
