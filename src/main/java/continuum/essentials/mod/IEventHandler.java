package continuum.essentials.mod;

public interface IEventHandler<E>
{
	public void execute(E event);
}
