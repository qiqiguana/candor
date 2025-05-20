package org.javathena.core.data;

import java.io.IOException;

public interface PersistenteData<T>
{
	public void save(T data) throws IOException;
	public T load() throws IOException;
}
