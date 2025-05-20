package net.sourceforge.beanbin.test;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ImplOne")
public class ImplOne extends Base {
	private static final long serialVersionUID = 8988890654754274830L;
}
