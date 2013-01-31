package module.jobBank.domain.utils;

public interface IPredicate<T> {
	public boolean evaluate(T object);
}
