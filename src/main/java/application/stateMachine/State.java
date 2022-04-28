package application.stateMachine;

import application.exceptions.BusinessLogicException;

public enum State {

    PREPARE {
        @Override
        public State changeState(Event event) {
           if (event == Event.EXECUTE) {
               return EXECUTION;
           }
           throw new BusinessLogicException("Trying to change status \"" + this.name() + "\" by event \"" + event.name() + "\"");
        }
    },
    EXECUTION {
        @Override
        public State changeState(Event event) {
            if (event == Event.CHECK) {

                return CONTROL;
            }
            throw new BusinessLogicException("Trying to change status \"" + this.name() + "\" by event \"" + event.name() + "\"");
        }
    },
    CONTROL {
        @Override
        public State changeState(Event event) {
            if (event == Event.REWORK) {
                return REWORK;
            }
            if (event == Event.FINISH) {
                return DONE;
            }
            throw new BusinessLogicException("Trying to change status \"" + this.name() + "\" by event \"" + event.name() + "\"");
        }
    },
    REWORK {
        @Override
        public State changeState(Event event) {
            if (event == Event.EXECUTE) {
                return EXECUTION;
            }
            throw new BusinessLogicException("Trying to change status \"" + this.name() + "\" by event \"" + event.name() + "\"");
        }
    },
    DONE {
        @Override
        public State changeState(Event event) {
            throw new BusinessLogicException("Trying to change status \"" + this.name() + "\" by event \"" + event.name() + "\"");
        }
    };
    public abstract State changeState(Event event);
}