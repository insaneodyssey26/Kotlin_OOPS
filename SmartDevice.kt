import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SmartDevice(val name: String, val category: String) { 
    // open class is also superclass, means bairer kono class eta theke inherit korte parbe, by default class final thake

    var deviceStatus = "online"
        protected set
        // protected set mane only this class and etar subclass ei variable ta modify (set) korte parbe

    open val deviceType = "unknown"
    // val mane read-only (can't be modified), but 'open val' hole jekono subclass theke etar value modify (override) kora jabe

    open fun turnOn() {
        deviceStatus = "on"
    }
    // open function mane kono subclass theke function ki kaj korbe seta modify kora jabe

    open fun turnOff() {
        deviceStatus = "off"
    }
}

class SmartTvDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart TV"
    // age deviceType 'open val' chilo, so akhon otar value modify kora hocche using override

    private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)
    // private var, mane only SmartTvDevice class eta k access korte parbe, others kono class parbe na 

    private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)
    // RangeRegulator, barbar get() set() use na kore delegate use kora hoy

    fun increaseSpeakerVolume() {
        speakerVolume++
        println("Speaker volume increased to $speakerVolume.")
    }

    fun nextChannel() {
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }

    override fun turnOn() {
        super.turnOn()  // super mane age 'open fun turnOn chilo' (superclass turnOn), ota call korche, oi superclass ta k
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                "set to $channelNumber."
        )
    }

    override fun turnOff() {
        super.turnOff()
        println("$name turned off")
    }
}

class SmartLightDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart Light"

    private var brightnessLevel by RangeRegulator(initialValue = 0, minValue = 0, maxValue = 100)

    fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }

    override fun turnOn() {
        super.turnOn()
        brightnessLevel = 2
        println("$name turned on. The brightness level is $brightnessLevel.")
    }

    override fun turnOff() {
        super.turnOff()
        brightnessLevel = 0
        println("Smart Light turned off")
    }
}

class SmartHome(  // SmartTvDevice, SmartLightDevice er property gulo SmartHome er read-only property gulo te store (maybe copy) hocche, not sure properly
    val smartTvDevice: SmartTvDevice,
    val smartLightDevice: SmartLightDevice
) {

    var deviceTurnOnCount = 0
        private set
        // deviceTurnOnCount is mutable, but private set howa mane only setter function ta private hoye gelo, means only SmartHome class eta modify korte parbe

    fun turnOnTv() {
        deviceTurnOnCount++
        smartTvDevice.turnOn()
    }

    fun turnOffTv() {
        deviceTurnOnCount--
        smartTvDevice.turnOff()
    }

    fun increaseTvVolume() {
        smartTvDevice.increaseSpeakerVolume()
    }

    fun changeTvChannelToNext() {
        smartTvDevice.nextChannel()
    }

    fun turnOnLight() {
        deviceTurnOnCount++
        smartLightDevice.turnOn()
    }

    fun turnOffLight() {
        deviceTurnOnCount--
        smartLightDevice.turnOff()
    }

    fun increaseLightBrightness() {
        smartLightDevice.increaseBrightness()
    }

    fun turnOffAllDevices() {
        turnOffTv()
        turnOffLight()
    }
}

class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int  // minValue, maxValue are private val (read-only), also private bole only RangeRegulator class tai eder access korte parbe, also type-safety is used here too
) : ReadWriteProperty<Any?, Int> {  // ReadWriteProperty use hoyeche, Int> mane holo, RangeRegulator is a delegate for Int properties

    var fieldData = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        // KProperty holds metadatas of properties
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }
}

fun main() {
    var smartDevice: SmartDevice = SmartTvDevice("Android TV", "Entertainment")
    // smartDevice is the varible, SmartDevice is a reference of type SmartDevice
    smartDevice.turnOn()

    smartDevice = SmartLightDevice("Google Light", "Utility")
    smartDevice.turnOn()
}