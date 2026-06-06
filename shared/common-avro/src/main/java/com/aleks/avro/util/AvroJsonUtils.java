package com.aleks.avro.util;

import java.io.ByteArrayOutputStream;

import lombok.experimental.UtilityClass;

import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;

import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;

@UtilityClass
public class AvroJsonUtils {

  public static <T extends SpecificRecord> String toJson(
      T record
  ) {

    try {

      ByteArrayOutputStream output =
          new ByteArrayOutputStream();

      DatumWriter<T> writer =
          new SpecificDatumWriter<>(
              (Class<T>) record.getClass()
          );

      Encoder encoder =
          EncoderFactory
              .get()
              .jsonEncoder(
                  record.getSchema(),
                  output
              );

      writer.write(
          record,
          encoder
      );

      encoder.flush();

      return output.toString();

    } catch (Exception ex) {

      throw new RuntimeException(
          "Failed to serialize Avro record",
          ex
      );
    }
  }
}
