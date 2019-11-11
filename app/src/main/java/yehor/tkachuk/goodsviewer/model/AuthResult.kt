package yehor.tkachuk.goodsviewer.model

class AuthResult private constructor(
    val state: State,
    val success: Boolean = false
){
    companion object{
        fun empty(): AuthResult{
            return AuthResult(State.EMPTY)
        }
        fun registered(): AuthResult{
            return AuthResult(State.REGISTER, true)
        }
        fun loggedIn(): AuthResult{
            return AuthResult(State.LOGIN, true)
        }
        fun notRegistered(): AuthResult{
            return AuthResult(State.REGISTER, false)
        }
        fun notLogged(): AuthResult{
            return AuthResult(State.LOGIN, false)
        }
    }
    enum class State{
        EMPTY,
        REGISTER,
        LOGIN
    }
}