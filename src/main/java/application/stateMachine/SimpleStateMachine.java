package application.stateMachine;

public class SimpleStateMachine {

    private State state;

    public void setState (Boolean is_done, Boolean is_verified) {
        if (is_done == null && is_verified == null) {
            state = State.PREPARE;
        } else if (!is_done && !is_verified) {
            state = State.EXECUTION;
        } else if(!is_done) {
            state = State.CONTROL;
        } else if(!is_verified) {
            state = State.REWORK;
        } else {
            state = State.DONE;
        }
    }

    public State getState() {
        return state;
    }

    public void sendEvent (Event event) {
        state = state.changeState(event);
    }
}
