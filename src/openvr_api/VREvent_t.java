package openvr_api;
import openvr_api.IOpenvr_api.EVREventType;
import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Name;
import org.bridj.ann.Namespace;
/**
 * An event posted by the server to all running applications<br>
 * <i>native declaration : /usr/include/stdint.h:595</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("VREvent_t") 
@Namespace("vr") 
@Library("openvr_api") 
public class VREvent_t extends StructObject {
	static {
		BridJ.register();
	}
	/** C type : EVREventType */
	@Field(0) 
	public IntValuedEnum<EVREventType > eventType() {
		return this.io.getEnumField(this, 0);
	}
	/** C type : EVREventType */
	@Field(0) 
	public VREvent_t eventType(IntValuedEnum<EVREventType > eventType) {
		this.io.setEnumField(this, 0, eventType);
		return this;
	}
	/** C type : TrackedDeviceIndex_t */
	@Field(1) 
	public int trackedDeviceIndex() {
		return this.io.getIntField(this, 1);
	}
	/** C type : TrackedDeviceIndex_t */
	@Field(1) 
	public VREvent_t trackedDeviceIndex(int trackedDeviceIndex) {
		this.io.setIntField(this, 1, trackedDeviceIndex);
		return this;
	}
	/** C type : VREvent_Data_t */
	@Field(2) 
	public VREvent_Data_t data() {
		return this.io.getNativeObjectField(this, 2);
	}
	/** C type : VREvent_Data_t */
	@Field(2) 
	public VREvent_t data(VREvent_Data_t data) {
		this.io.setNativeObjectField(this, 2, data);
		return this;
	}
	@Field(3) 
	public float eventAgeSeconds() {
		return this.io.getFloatField(this, 3);
	}
	@Field(3) 
	public VREvent_t eventAgeSeconds(float eventAgeSeconds) {
		this.io.setFloatField(this, 3, eventAgeSeconds);
		return this;
	}
	public VREvent_t() {
		super();
	}
	public VREvent_t(Pointer pointer) {
		super(pointer);
	}
}