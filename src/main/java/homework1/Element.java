package homework1;

public class Element {
    final private String name;

    public String getName() {
        return name;
    }

    private Element(Builder builder) {
        this.name = builder.name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public static class Builder{
        private String name;

        public Element.Builder name(String name) {
            this.name = name;
            return this;
        }

        public Element build() {
            return new Element(this);
        }
    }


}
