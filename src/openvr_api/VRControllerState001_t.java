package openvr_api;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Name;
import org.bridj.ann.Namespace;
/**
 * Holds all the state of a controller at one moment in time.<br>
 * <i>native declaration : /usr/include/stdint.h:642</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("VRControllerState001_t") 
@Namespace("vr") 
@Library("openvr_api") 
public class VRControllerState001_t extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public int unPacketNum() {
		return this.io.getIntField(this, 0);
	}
	@Field(0) 
	public VRControllerState001_t unPacketNum(int unPacketNum) {
		this.io.setIntField(this, 0, unPacketNum);
		return this;
	}
	@Field(1) 
	public long ulButtonPressed() {
		return this.io.getLongField(this, 1);
	}
	@Field(1) 
	public VRControllerState001_t ulButtonPressed(long ulButtonPressed) {
		this.io.setLongField(this, 1, ulButtonPressed);
		return this;
	}
	@Field(2) 
	public long ulButtonTouched() {
		return this.io.getLongField(this, 2);
	}
	@Field(2) 
	public VRControllerState001_t ulButtonTouched(long ulButtonTouched) {
		this.io.setLongField(this, 2, ulButtonTouched);
		return this;
	}
	/** C type : VRControllerAxis_t[k_unControllerStateAxisCount] */
	@Array({openvr_api.Openvr_apiLibrary.k_unControllerStateAxisCount}) 
	@Field(3) 
	public Pointer<VRControllerAxis_t > rAxis() {
		return this.io.getPointerField(this, 3);
	}
	public VRControllerState001_t() {
		super();
	}
	public VRControllerState001_t(Pointer pointer) {
		super(pointer);
	}
}