// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SortingMessage.proto

package ru.spbau.kirilenko;

public final class SortingMessage {
  private SortingMessage() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface SortOrBuilder extends
      // @@protoc_insertion_point(interface_extends:messages.Sort)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated int32 arr = 1;</code>
     */
    java.util.List<java.lang.Integer> getArrList();
    /**
     * <code>repeated int32 arr = 1;</code>
     */
    int getArrCount();
    /**
     * <code>repeated int32 arr = 1;</code>
     */
    int getArr(int index);
  }
  /**
   * Protobuf type {@code messages.Sort}
   */
  public  static final class Sort extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:messages.Sort)
      SortOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Sort.newBuilder() to construct.
    private Sort(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Sort() {
      arr_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Sort(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                arr_ = new java.util.ArrayList<java.lang.Integer>();
                mutable_bitField0_ |= 0x00000001;
              }
              arr_.add(input.readInt32());
              break;
            }
            case 10: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001) && input.getBytesUntilLimit() > 0) {
                arr_ = new java.util.ArrayList<java.lang.Integer>();
                mutable_bitField0_ |= 0x00000001;
              }
              while (input.getBytesUntilLimit() > 0) {
                arr_.add(input.readInt32());
              }
              input.popLimit(limit);
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
          arr_ = java.util.Collections.unmodifiableList(arr_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ru.spbau.kirilenko.SortingMessage.internal_static_messages_Sort_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ru.spbau.kirilenko.SortingMessage.internal_static_messages_Sort_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ru.spbau.kirilenko.SortingMessage.Sort.class, ru.spbau.kirilenko.SortingMessage.Sort.Builder.class);
    }

    public static final int ARR_FIELD_NUMBER = 1;
    private java.util.List<java.lang.Integer> arr_;
    /**
     * <code>repeated int32 arr = 1;</code>
     */
    public java.util.List<java.lang.Integer>
        getArrList() {
      return arr_;
    }
    /**
     * <code>repeated int32 arr = 1;</code>
     */
    public int getArrCount() {
      return arr_.size();
    }
    /**
     * <code>repeated int32 arr = 1;</code>
     */
    public int getArr(int index) {
      return arr_.get(index);
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      for (int i = 0; i < arr_.size(); i++) {
        output.writeInt32(1, arr_.get(i));
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      {
        int dataSize = 0;
        for (int i = 0; i < arr_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(arr_.get(i));
        }
        size += dataSize;
        size += 1 * getArrList().size();
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof ru.spbau.kirilenko.SortingMessage.Sort)) {
        return super.equals(obj);
      }
      ru.spbau.kirilenko.SortingMessage.Sort other = (ru.spbau.kirilenko.SortingMessage.Sort) obj;

      boolean result = true;
      result = result && getArrList()
          .equals(other.getArrList());
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (getArrCount() > 0) {
        hash = (37 * hash) + ARR_FIELD_NUMBER;
        hash = (53 * hash) + getArrList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static ru.spbau.kirilenko.SortingMessage.Sort parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ru.spbau.kirilenko.SortingMessage.Sort parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ru.spbau.kirilenko.SortingMessage.Sort parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ru.spbau.kirilenko.SortingMessage.Sort parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ru.spbau.kirilenko.SortingMessage.Sort parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ru.spbau.kirilenko.SortingMessage.Sort parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ru.spbau.kirilenko.SortingMessage.Sort parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ru.spbau.kirilenko.SortingMessage.Sort parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static ru.spbau.kirilenko.SortingMessage.Sort parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static ru.spbau.kirilenko.SortingMessage.Sort parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static ru.spbau.kirilenko.SortingMessage.Sort parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ru.spbau.kirilenko.SortingMessage.Sort parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(ru.spbau.kirilenko.SortingMessage.Sort prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code messages.Sort}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:messages.Sort)
        ru.spbau.kirilenko.SortingMessage.SortOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ru.spbau.kirilenko.SortingMessage.internal_static_messages_Sort_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ru.spbau.kirilenko.SortingMessage.internal_static_messages_Sort_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                ru.spbau.kirilenko.SortingMessage.Sort.class, ru.spbau.kirilenko.SortingMessage.Sort.Builder.class);
      }

      // Construct using ru.spbau.kirilenko.SortingMessage.Sort.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        arr_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ru.spbau.kirilenko.SortingMessage.internal_static_messages_Sort_descriptor;
      }

      public ru.spbau.kirilenko.SortingMessage.Sort getDefaultInstanceForType() {
        return ru.spbau.kirilenko.SortingMessage.Sort.getDefaultInstance();
      }

      public ru.spbau.kirilenko.SortingMessage.Sort build() {
        ru.spbau.kirilenko.SortingMessage.Sort result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public ru.spbau.kirilenko.SortingMessage.Sort buildPartial() {
        ru.spbau.kirilenko.SortingMessage.Sort result = new ru.spbau.kirilenko.SortingMessage.Sort(this);
        int from_bitField0_ = bitField0_;
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          arr_ = java.util.Collections.unmodifiableList(arr_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.arr_ = arr_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof ru.spbau.kirilenko.SortingMessage.Sort) {
          return mergeFrom((ru.spbau.kirilenko.SortingMessage.Sort)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(ru.spbau.kirilenko.SortingMessage.Sort other) {
        if (other == ru.spbau.kirilenko.SortingMessage.Sort.getDefaultInstance()) return this;
        if (!other.arr_.isEmpty()) {
          if (arr_.isEmpty()) {
            arr_ = other.arr_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureArrIsMutable();
            arr_.addAll(other.arr_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        ru.spbau.kirilenko.SortingMessage.Sort parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (ru.spbau.kirilenko.SortingMessage.Sort) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.util.List<java.lang.Integer> arr_ = java.util.Collections.emptyList();
      private void ensureArrIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          arr_ = new java.util.ArrayList<java.lang.Integer>(arr_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
       * <code>repeated int32 arr = 1;</code>
       */
      public java.util.List<java.lang.Integer>
          getArrList() {
        return java.util.Collections.unmodifiableList(arr_);
      }
      /**
       * <code>repeated int32 arr = 1;</code>
       */
      public int getArrCount() {
        return arr_.size();
      }
      /**
       * <code>repeated int32 arr = 1;</code>
       */
      public int getArr(int index) {
        return arr_.get(index);
      }
      /**
       * <code>repeated int32 arr = 1;</code>
       */
      public Builder setArr(
          int index, int value) {
        ensureArrIsMutable();
        arr_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 arr = 1;</code>
       */
      public Builder addArr(int value) {
        ensureArrIsMutable();
        arr_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 arr = 1;</code>
       */
      public Builder addAllArr(
          java.lang.Iterable<? extends java.lang.Integer> values) {
        ensureArrIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, arr_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 arr = 1;</code>
       */
      public Builder clearArr() {
        arr_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:messages.Sort)
    }

    // @@protoc_insertion_point(class_scope:messages.Sort)
    private static final ru.spbau.kirilenko.SortingMessage.Sort DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ru.spbau.kirilenko.SortingMessage.Sort();
    }

    public static ru.spbau.kirilenko.SortingMessage.Sort getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<Sort>
        PARSER = new com.google.protobuf.AbstractParser<Sort>() {
      public Sort parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Sort(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Sort> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Sort> getParserForType() {
      return PARSER;
    }

    public ru.spbau.kirilenko.SortingMessage.Sort getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_messages_Sort_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_messages_Sort_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024SortingMessage.proto\022\010messages\"\023\n\004Sort" +
      "\022\013\n\003arr\030\001 \003(\005B$\n\022ru.spbau.kirilenkoB\016Sor" +
      "tingMessage"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_messages_Sort_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_messages_Sort_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_messages_Sort_descriptor,
        new java.lang.String[] { "Arr", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
