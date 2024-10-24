package crynet.testovoe2.Mappers;

public interface Mapper<A, B> {
B mapTo (A a);
A mapFrom(B b);
}
