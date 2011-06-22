package br.ufpe.cin.algoritmos.db;

public class ObjectType implements Comparable<ObjectType> {

	private Integer id;
	private String typeName;
	private Class<?> runtimeClass;

	public ObjectType(Integer id, String typeName, Class<?> runtimeClass) {
		this.id = id;
		this.typeName = typeName;
		this.runtimeClass = runtimeClass;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setObjClass(Class<?> objClass) {
		this.runtimeClass = objClass;
	}

	public Class<?> getObjClass() {
		return runtimeClass;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean ret = false;
		if (obj instanceof ObjectType)
			ret = ((ObjectType) obj).id.equals(id);
		return ret;
	}

	@Override
	public int compareTo(ObjectType o) {
		if (o == null)
			throw new NullPointerException("argument to compare can't be null");
		return id.compareTo(o.id);
	}

}
