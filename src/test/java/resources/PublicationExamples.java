package resources;

public class PublicationExamples {

    private static class Resource {

        private final int resourceCount;

        public Resource(int resourceCount) {
            this.resourceCount = resourceCount;
        }

        public int getResourceCount() {
            return this.resourceCount;
        }
    }


    //not save publication
    private class UnsafeLazyInit {

        private static Resource resource;

        public static Resource getInstance() {
            if (resource == null) {
                resource = new Resource(10); // not safe publication
            }

            return resource;
        }

    }

    //safe publication
    private class SafeLazyInit {

        private static Resource resource;

        public synchronized static Resource getInstance() {
            if (resource == null) {
                resource = new Resource(10);
            }

            return resource;
        }
    }

    //one else safe publication
    private class EagerInit {

        private static Resource resource = new Resource(10);

        public static Resource getInstance() {
            return resource;
        }

    }

    //one else safe publication with holder

    private class ResourceFactory {

        private static class ResourceHolder {

            public static Resource resource = new Resource(10);
        }

        public static Resource getResource() {
            return ResourceHolder.resource;
        }
    }
}
