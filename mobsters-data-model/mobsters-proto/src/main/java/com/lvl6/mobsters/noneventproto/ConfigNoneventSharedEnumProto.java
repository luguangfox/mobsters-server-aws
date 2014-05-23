// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ConfigNoneventSharedEnum.proto

package com.lvl6.mobsters.noneventproto;

public final class ConfigNoneventSharedEnumProto {
  private ConfigNoneventSharedEnumProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public enum DayOfWeek
      implements com.google.protobuf.ProtocolMessageEnum {
    SUNDAY(0, 1),
    MONDAY(1, 2),
    TUESDAY(2, 3),
    WEDNESDAY(3, 4),
    THURSDAY(4, 5),
    FRIDAY(5, 6),
    SATURDAY(6, 7),
    NO_DAY_OF_WEEK(7, 8),
    ;
    
    public static final int SUNDAY_VALUE = 1;
    public static final int MONDAY_VALUE = 2;
    public static final int TUESDAY_VALUE = 3;
    public static final int WEDNESDAY_VALUE = 4;
    public static final int THURSDAY_VALUE = 5;
    public static final int FRIDAY_VALUE = 6;
    public static final int SATURDAY_VALUE = 7;
    public static final int NO_DAY_OF_WEEK_VALUE = 8;
    
    
    public final int getNumber() { return value; }
    
    public static DayOfWeek valueOf(int value) {
      switch (value) {
        case 1: return SUNDAY;
        case 2: return MONDAY;
        case 3: return TUESDAY;
        case 4: return WEDNESDAY;
        case 5: return THURSDAY;
        case 6: return FRIDAY;
        case 7: return SATURDAY;
        case 8: return NO_DAY_OF_WEEK;
        default: return null;
      }
    }
    
    public static com.google.protobuf.Internal.EnumLiteMap<DayOfWeek>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<DayOfWeek>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<DayOfWeek>() {
            public DayOfWeek findValueByNumber(int number) {
              return DayOfWeek.valueOf(number);
            }
          };
    
    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.lvl6.mobsters.noneventproto.ConfigNoneventSharedEnumProto.getDescriptor().getEnumTypes().get(0);
    }
    
    private static final DayOfWeek[] VALUES = {
      SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, NO_DAY_OF_WEEK, 
    };
    
    public static DayOfWeek valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }
    
    private final int index;
    private final int value;
    
    private DayOfWeek(int index, int value) {
      this.index = index;
      this.value = value;
    }
    
    // @@protoc_insertion_point(enum_scope:proto.DayOfWeek)
  }
  
  public enum Element
      implements com.google.protobuf.ProtocolMessageEnum {
    FIRE(0, 1),
    EARTH(1, 2),
    WATER(2, 3),
    LIGHT(3, 4),
    DARK(4, 5),
    ROCK(5, 6),
    NO_ELEMENT(6, 7),
    ;
    
    public static final int FIRE_VALUE = 1;
    public static final int EARTH_VALUE = 2;
    public static final int WATER_VALUE = 3;
    public static final int LIGHT_VALUE = 4;
    public static final int DARK_VALUE = 5;
    public static final int ROCK_VALUE = 6;
    public static final int NO_ELEMENT_VALUE = 7;
    
    
    public final int getNumber() { return value; }
    
    public static Element valueOf(int value) {
      switch (value) {
        case 1: return FIRE;
        case 2: return EARTH;
        case 3: return WATER;
        case 4: return LIGHT;
        case 5: return DARK;
        case 6: return ROCK;
        case 7: return NO_ELEMENT;
        default: return null;
      }
    }
    
    public static com.google.protobuf.Internal.EnumLiteMap<Element>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<Element>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<Element>() {
            public Element findValueByNumber(int number) {
              return Element.valueOf(number);
            }
          };
    
    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.lvl6.mobsters.noneventproto.ConfigNoneventSharedEnumProto.getDescriptor().getEnumTypes().get(1);
    }
    
    private static final Element[] VALUES = {
      FIRE, EARTH, WATER, LIGHT, DARK, ROCK, NO_ELEMENT, 
    };
    
    public static Element valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }
    
    private final int index;
    private final int value;
    
    private Element(int index, int value) {
      this.index = index;
      this.value = value;
    }
    
    // @@protoc_insertion_point(enum_scope:proto.Element)
  }
  
  public enum Quality
      implements com.google.protobuf.ProtocolMessageEnum {
    NO_QUALITY(0, 1),
    COMMON(1, 2),
    RARE(2, 3),
    ULTRA(3, 4),
    EPIC(4, 5),
    LEGENDARY(5, 6),
    EVO(6, 7),
    ;
    
    public static final int NO_QUALITY_VALUE = 1;
    public static final int COMMON_VALUE = 2;
    public static final int RARE_VALUE = 3;
    public static final int ULTRA_VALUE = 4;
    public static final int EPIC_VALUE = 5;
    public static final int LEGENDARY_VALUE = 6;
    public static final int EVO_VALUE = 7;
    
    
    public final int getNumber() { return value; }
    
    public static Quality valueOf(int value) {
      switch (value) {
        case 1: return NO_QUALITY;
        case 2: return COMMON;
        case 3: return RARE;
        case 4: return ULTRA;
        case 5: return EPIC;
        case 6: return LEGENDARY;
        case 7: return EVO;
        default: return null;
      }
    }
    
    public static com.google.protobuf.Internal.EnumLiteMap<Quality>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<Quality>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<Quality>() {
            public Quality findValueByNumber(int number) {
              return Quality.valueOf(number);
            }
          };
    
    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.lvl6.mobsters.noneventproto.ConfigNoneventSharedEnumProto.getDescriptor().getEnumTypes().get(2);
    }
    
    private static final Quality[] VALUES = {
      NO_QUALITY, COMMON, RARE, ULTRA, EPIC, LEGENDARY, EVO, 
    };
    
    public static Quality valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }
    
    private final int index;
    private final int value;
    
    private Quality(int index, int value) {
      this.index = index;
      this.value = value;
    }
    
    // @@protoc_insertion_point(enum_scope:proto.Quality)
  }
  
  public enum ResourceType
      implements com.google.protobuf.ProtocolMessageEnum {
    NO_RESOURCE(0, 4),
    CASH(1, 1),
    OIL(2, 2),
    GEMS(3, 3),
    MONSTER(4, 20),
    ;
    
    public static final int NO_RESOURCE_VALUE = 4;
    public static final int CASH_VALUE = 1;
    public static final int OIL_VALUE = 2;
    public static final int GEMS_VALUE = 3;
    public static final int MONSTER_VALUE = 20;
    
    
    public final int getNumber() { return value; }
    
    public static ResourceType valueOf(int value) {
      switch (value) {
        case 4: return NO_RESOURCE;
        case 1: return CASH;
        case 2: return OIL;
        case 3: return GEMS;
        case 20: return MONSTER;
        default: return null;
      }
    }
    
    public static com.google.protobuf.Internal.EnumLiteMap<ResourceType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<ResourceType>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<ResourceType>() {
            public ResourceType findValueByNumber(int number) {
              return ResourceType.valueOf(number);
            }
          };
    
    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.lvl6.mobsters.noneventproto.ConfigNoneventSharedEnumProto.getDescriptor().getEnumTypes().get(3);
    }
    
    private static final ResourceType[] VALUES = {
      NO_RESOURCE, CASH, OIL, GEMS, MONSTER, 
    };
    
    public static ResourceType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }
    
    private final int index;
    private final int value;
    
    private ResourceType(int index, int value) {
      this.index = index;
      this.value = value;
    }
    
    // @@protoc_insertion_point(enum_scope:proto.ResourceType)
  }
  
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\036ConfigNoneventSharedEnum.proto\022\005proto*" +
      "{\n\tDayOfWeek\022\n\n\006SUNDAY\020\001\022\n\n\006MONDAY\020\002\022\013\n\007" +
      "TUESDAY\020\003\022\r\n\tWEDNESDAY\020\004\022\014\n\010THURSDAY\020\005\022\n" +
      "\n\006FRIDAY\020\006\022\014\n\010SATURDAY\020\007\022\022\n\016NO_DAY_OF_WE" +
      "EK\020\010*X\n\007Element\022\010\n\004FIRE\020\001\022\t\n\005EARTH\020\002\022\t\n\005" +
      "WATER\020\003\022\t\n\005LIGHT\020\004\022\010\n\004DARK\020\005\022\010\n\004ROCK\020\006\022\016" +
      "\n\nNO_ELEMENT\020\007*\\\n\007Quality\022\016\n\nNO_QUALITY\020" +
      "\001\022\n\n\006COMMON\020\002\022\010\n\004RARE\020\003\022\t\n\005ULTRA\020\004\022\010\n\004EP" +
      "IC\020\005\022\r\n\tLEGENDARY\020\006\022\007\n\003EVO\020\007*I\n\014Resource" +
      "Type\022\017\n\013NO_RESOURCE\020\004\022\010\n\004CASH\020\001\022\007\n\003OIL\020\002",
      "\022\010\n\004GEMS\020\003\022\013\n\007MONSTER\020\024B@\n\037com.lvl6.mobs" +
      "ters.noneventprotoB\035ConfigNoneventShared" +
      "EnumProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
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
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}
