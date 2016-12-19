package domain;

//TODO Использовать ли название surrogateID или id?
//TODO Если мы используем в OrderItem productId и в Order customerId, то нам необходим метод getId. Если же мы используем в OrderItem и в Order бизнес-ключи на Product и Customer соответственно, тогда можно удалить getId и пользовать методы доступа к бизнес-ключу

public abstract class Entity <T> {
    private long id;

    public long getId() {
        return id;
    }

    public abstract T getBusinessKey ();
}
