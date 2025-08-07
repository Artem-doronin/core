import java.util.Stack;

public class MyStringBuilder {
    private StringBuilder builder;
    private Stack<Momento> history;

    public MyStringBuilder() {
        builder = new StringBuilder();
        history = new Stack<>();
    }

    public void saveStage() {
        history.push(new Momento(builder.toString()));
    }

    public MyStringBuilder append(String s) {
        saveStage();
        builder.append(s);
        return this;
    }

    public void undo() {
        if (!history.isEmpty()) {
            Momento lastSage = history.pop();
            builder = new StringBuilder(lastSage.getStage());
        }
    }

    @Override
    public String toString() {
        return builder.toString();
    }


    private static class Momento {
        private final String stage;

        public Momento(String stage) {
            this.stage = stage;
        }

        public String getStage() {
            return stage;
        }
    }
}
