package sim

import chisel3._
import chisel3.util.experimental.loadMemoryFromFile

import io.SramIO
import consts.Constants._

// for simulation only
class ROM(initFile: String) extends Module {
  var io = IO(Flipped(new SramIO(ADDR_WIDTH, INST_WIDTH)))

  var rom   = Mem(256, UInt(INST_WIDTH.W))
  val data  = RegInit(0.U(INST_WIDTH.W))
  val addr  = io.addr - RESET_PC
  loadMemoryFromFile(rom, initFile)

  when (io.en) {
    data := rom(addr(ADDR_WIDTH - 1, ADDR_ALIGN_WIDTH))
  } .otherwise {
    data := 0.U
  }

  io.valid := true.B
  io.rdata := data
}
