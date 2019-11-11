package yehor.tkachuk.goodsviewer.model

class SaveResult private constructor(
    val state: State,
    val message: String = ""
){
    companion object{
        fun saved(message: String = ""): SaveResult{
            return SaveResult(State.SAVED, message)
        }
        fun exists(message: String = ""): SaveResult{
            return SaveResult(State.EXISTS, message)
        }
        fun error(message: String = ""): SaveResult{
            return SaveResult(State.ERROR, message)
        }
    }

    enum class State{
        SAVED,
        EXISTS,
        ERROR
    }
}