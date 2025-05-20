package net.sourceforge.beanbin.test;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ImplTwo")
public class ImplTwo extends Base {
	private static final long serialVersionUID = -6352759755020833479L;
}
