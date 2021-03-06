package lodz.jug.kotlin.reactive.spring.reactor.creation

import lodz.jug.kotlin.reactive.spring.ThreadOps
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.core.scheduler.Schedulers
import java.util.function.Consumer

fun main(args: Array<String>) {
    creationWithCustomSource()
}


fun creationWithCustomSource(){

    class SignalSource(private val sink: FluxSink<String>){
        fun signal(s:String) : Unit {
            sink.next(s)
        }

        fun complete(){
            sink.complete()
        }
    }

    lateinit var source:SignalSource

    val f=Flux.create<String>{sink ->
            source= SignalSource(sink)
    }


    f.log()
     .publishOn(Schedulers.parallel())
     .subscribe{e->
        println("emitted element in ${ThreadOps.threadName()} $e")
    }


    source.signal("customSignal1")
    source.signal("customSignal2")
    source.complete()

}