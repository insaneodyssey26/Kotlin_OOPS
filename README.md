# My Kotlin OOP Learning Notes

Personal code examples to understand Object-Oriented Programming in Kotlin.

## What's Here

One file: `SmartDevice.kt` - A smart home example covering all the OOP basics I need to learn.

## OOP Concepts I'm Learning

**Inheritance** - `SmartTvDevice` and `SmartLightDevice` inherit from `SmartDevice`

**Encapsulation** - Properties like `speakerVolume` are private, accessed through methods

**Polymorphism** - Same `turnOn()` method works differently for TV vs Light

**Property Delegation** - `RangeRegulator` automatically validates values (volume 0-100, etc.)

## Quick Reference

- `open class` = can be inherited
- `override` = replace parent method
- `private` = only this class can access
- `protected set` = only this class/subclasses can modify
- `by RangeRegulator()` = delegate property with auto-validation

## How to Run
```bash
kotlin SmartDevice.kt
```

---
*Just my personal learning material, customized for how I understand things best.*
