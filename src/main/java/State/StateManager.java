package State;

/**
 * Created by hiroto on 2015/02/23.
 */
public class StateManager implements Context{
        private State state = null;

        //一番最初のState
        public StateManager(){
            state = Bokou.getInstance();
        }

        public void setState(State state){
            this.state = state;
        }
        //現在の状態を返す
        public State getState() {
            return state;
        }


        //現在のstateを引数に遷移させる.
        @Override
        public boolean transition(State state,String dest) {
            if(state.transition(this,state,dest))
                return true;
            else
                return false;
        }

        //現在のStateを調整するメソッド
        @Override
        public boolean transitionAdjuster(Context context,State state) {
            if(state.transitionAjuster(this,state))
                return true;
            else
                return false;
        }

        @Override
        public boolean transitionBokou(State state) {
            if(state.transitionBokou(this,state)) {
                return true;
            }else {
                return false;
            }
        }
}

