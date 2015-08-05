package com.fourthskyinteractive.dx4j.dxgi.adapter;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

import com.fourthskyinteractive.dx4j.windows.HMONITOR;
/**
 * <i>native declaration : DXGI.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("dxgi") 
public class DXGI_OUTPUT_DESC extends StructObject {
	public DXGI_OUTPUT_DESC() {
		super();
	}
	public DXGI_OUTPUT_DESC(Pointer<? extends StructObject> pointer) {
		super(pointer);
	}
	/// C type : WCHAR[32]
	@Array({32}) 
	@Field(0) 
	public Pointer<Short > DeviceName() {
		return this.io.getPointerField(this, 0);
	}
	@Field(2) 
	public int AttachedToDesktop() {
		return this.io.getIntField(this, 2);
	}
	@Field(2) 
	public DXGI_OUTPUT_DESC AttachedToDesktop(int AttachedToDesktop) {
		this.io.setIntField(this, 2, AttachedToDesktop);
		return this;
	}
	/// Conversion Error : DXGI_MODE_ROTATION (Unsupported type)
	/// C type : HMONITOR
	@Field(3) 
	public HMONITOR Monitor() {
		return this.io.getTypedPointerField(this, 3);
	}
	/// C type : HMONITOR
	@SuppressWarnings("unchecked")
	@Field(3) 
	public DXGI_OUTPUT_DESC Monitor(HMONITOR Monitor) {
		this.io.setPointerField(this, 3, Monitor);
		return this;
	}
}